package ar.org.cpcemza.prodemundial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HealthController {

    /**
     * GET /health
     * Ruta pública — no requiere autenticación.
     * Usada por UptimeRobot para mantener el servidor despierto
     * y por Render para verificar que el servicio arrancó.
     *
     * IMPORTANTE: agregar "/health" a las rutas permitidas
     * en SecurityConfig.java:
     *   .requestMatchers("/health", "/api/auth/login", ...).permitAll()
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status",    "UP",
                "app",       "Prode Mundial 2026 - CPCE Mendoza",
                "timestamp", LocalDateTime.now().toString()
        ));
    }
}