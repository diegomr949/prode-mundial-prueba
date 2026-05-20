package ar.org.cpcemza.prodemundial.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rate limiting por IP usando ventana deslizante simple (token bucket).
 *
 * Protege especialmente el endpoint de login contra:
 *  - Ataques de fuerza bruta de contraseñas
 *  - Credential stuffing
 *  - Enumeración de usuarios
 *
 * En producción con múltiples instancias, reemplazar el ConcurrentHashMap
 * por Redis usando Spring Data Redis + Bucket4j-Redis.
 */
@Slf4j
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    @Value("${rate-limit.auth-requests-per-minute:10}")
    private int authRequestsPerMinute;

    @Value("${rate-limit.api-requests-per-minute:120}")
    private int apiRequestsPerMinute;

    // Map: IP → (contador, ventana_inicio_ms)
    private final ConcurrentHashMap<String, long[]> authBuckets = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, long[]> apiBuckets  = new ConcurrentHashMap<>();

    private static final long WINDOW_MS = 60_000L; // 1 minuto

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String ip   = getClientIp(request);
        final String path = request.getRequestURI();

        // Aplicar rate limit diferenciado por tipo de endpoint
        if (path.startsWith("/api/auth/")) {
            if (!isAllowed(ip, authBuckets, authRequestsPerMinute)) {
                log.warn("[RateLimit] Auth bloqueado - IP: {} Path: {}", ip, path);
                rejectRequest(response, "Demasiados intentos. Esperá 1 minuto e intentá de nuevo.");
                return;
            }
        } else if (path.startsWith("/api/")) {
            if (!isAllowed(ip, apiBuckets, apiRequestsPerMinute)) {
                log.warn("[RateLimit] API bloqueado - IP: {} Path: {}", ip, path);
                rejectRequest(response, "Límite de solicitudes alcanzado. Intentá más tarde.");
                return;
        }
        }

        chain.doFilter(request, response);
    }

    private boolean isAllowed(String ip,
                               ConcurrentHashMap<String, long[]> buckets,
                               int maxRequests) {
        final long now = Instant.now().toEpochMilli();

        // [0] = timestamp inicio de ventana, [1] = contador de requests
        long[] bucket = buckets.compute(ip, (k, existing) -> {
            if (existing == null || now - existing[0] > WINDOW_MS) {
                return new long[]{ now, 1L };          // Nueva ventana
            }
            existing[1]++;
            return existing;
        });

        return bucket[1] <= maxRequests;
    }

    private String getClientIp(HttpServletRequest request) {
        // Leer IP real detrás de proxies/load balancers (Render, Railway, Nginx)
        // Solo confiar en estos headers si el proxy es de confianza
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isBlank()) {
            // X-Forwarded-For puede ser "client, proxy1, proxy2" — tomar el primero
            return ip.split(",")[0].trim();
        }
        ip = request.getHeader("X-Real-IP");
        if (ip != null && !ip.isBlank()) return ip.trim();
        return request.getRemoteAddr();
    }

    private void rejectRequest(HttpServletResponse response, String message)
            throws IOException {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Retry-After", "60");
        response.getWriter().write(
            String.format("{\"status\":429,\"error\":\"%s\"}", message)
        );
    }

    // Limpieza periódica para evitar memory leak (cada 10000 requests aprox)
    // En producción usar @Scheduled en lugar de este enfoque
    private final AtomicInteger cleanupCounter = new AtomicInteger(0);

    private void maybeCleanup() {
        if (cleanupCounter.incrementAndGet() % 10_000 == 0) {
            final long now = Instant.now().toEpochMilli();
            authBuckets.entrySet().removeIf(e -> now - e.getValue()[0] > WINDOW_MS * 10);
            apiBuckets.entrySet().removeIf(e -> now - e.getValue()[0] > WINDOW_MS * 10);
        }
    }
}
