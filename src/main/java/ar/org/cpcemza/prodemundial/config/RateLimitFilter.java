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
 * Rate limiting por IP.
 * Sigue siendo necesario aunque no haya JWT —
 * protege el endpoint de login contra fuerza bruta.
 */
@Slf4j
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    @Value("${rate-limit.auth-requests-per-minute:10}")
    private int authLimit;

    @Value("${rate-limit.api-requests-per-minute:120}")
    private int apiLimit;

    private final ConcurrentHashMap<String, long[]> authBuckets = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, long[]> apiBuckets  = new ConcurrentHashMap<>();
    private static final long WINDOW_MS = 60_000L;
    private final AtomicInteger cleanupCounter = new AtomicInteger(0);

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String ip   = getIp(req);
        final String path = req.getRequestURI();

        if (path.startsWith("/api/auth/")) {
            if (!isAllowed(ip, authBuckets, authLimit)) {
                log.warn("[RateLimit] Auth bloqueado — IP: {}", ip);
                reject(res, "Demasiados intentos. Esperá 1 minuto.");
                return;
            }
        } else if (path.startsWith("/api/")) {
            if (!isAllowed(ip, apiBuckets, apiLimit)) {
                reject(res, "Límite de solicitudes alcanzado.");
                return;
            }
        }

        if (cleanupCounter.incrementAndGet() % 5_000 == 0) cleanup();
        chain.doFilter(req, res);
    }

    private boolean isAllowed(String ip, ConcurrentHashMap<String, long[]> buckets, int max) {
        final long now = Instant.now().toEpochMilli();
        long[] b = buckets.compute(ip, (k, e) -> {
            if (e == null || now - e[0] > WINDOW_MS) return new long[]{ now, 1L };
            e[1]++;
            return e;
        });
        return b[1] <= max;
    }

    private String getIp(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isBlank()) return ip.split(",")[0].trim();
        ip = req.getHeader("X-Real-IP");
        if (ip != null && !ip.isBlank()) return ip.trim();
        return req.getRemoteAddr();
    }

    private void reject(HttpServletResponse res, String msg) throws IOException {
        res.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setHeader("Retry-After", "60");
        res.getWriter().write("{\"status\":429,\"error\":\"" + msg + "\"}");
    }

    private void cleanup() {
        final long now = Instant.now().toEpochMilli();
        authBuckets.entrySet().removeIf(e -> now - e.getValue()[0] > WINDOW_MS * 10);
        apiBuckets.entrySet().removeIf(e -> now - e.getValue()[0] > WINDOW_MS * 10);
    }
}