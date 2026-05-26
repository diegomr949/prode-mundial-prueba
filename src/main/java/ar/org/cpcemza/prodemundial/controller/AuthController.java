package ar.org.cpcemza.prodemundial.controller;

import ar.org.cpcemza.prodemundial.dto.AuthResponseDTO;
import ar.org.cpcemza.prodemundial.dto.LoginRequestDTO;
import ar.org.cpcemza.prodemundial.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * POST /api/auth/login
     * Autentica al usuario y crea una sesión de servidor.
     * El navegador guarda automáticamente la cookie JSESSIONID.
     * No se devuelve ningún token.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto,
            HttpServletRequest request
    ) {
        AuthResponseDTO response = authService.login(dto);
        // Forzar creación de nueva sesión (protección contra session fixation)
        request.getSession(true);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/auth/me
     * Verifica si hay una sesión activa y devuelve los datos del usuario.
     * El frontend lo llama al iniciar la app para restaurar el estado.
     * Devuelve 401 automáticamente si no hay sesión (Spring Security).
     */
    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails userDetails) {
        // 1. Si no hay sesión, devolvemos 401 limpio para que el Frontend sepa que debe mostrar el Login
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay sesión activa");
        }

        // 2. Si hay sesión, buscamos al usuario normal (ajustá esto a como lo tenías)
        // Ejemplo:
        // AuthResponseDTO response = authService.obtenerDatosUsuario(userDetails.getUsername());
        // return ResponseEntity.ok(response);

        return ResponseEntity.ok(userDetails); // Reemplazalo por tu lógica original
    }

    /**
     * POST /api/auth/logout
     * Invalida la sesión del servidor y elimina la cookie.
     * Spring Security lo maneja automáticamente via SecurityConfig.
     * Este endpoint solo existe para que el frontend tenga una URL clara.
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return ResponseEntity.ok(Map.of("mensaje", "Sesión cerrada correctamente."));
    }

    /**
     * GET /api/auth/expirada
     * Spring redirige aquí cuando la sesión expira.
     * El frontend lo recibe como 401 y muestra el login.
     */
    @GetMapping("/expirada")
    public ResponseEntity<Map<String, String>> expirada() {
        return ResponseEntity.status(401)
                .body(Map.of("error", "Tu sesión expiró. Por favor iniciá sesión nuevamente."));
    }
}