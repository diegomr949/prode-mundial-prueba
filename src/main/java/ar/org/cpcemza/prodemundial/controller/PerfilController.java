package ar.org.cpcemza.prodemundial.controller;

import ar.org.cpcemza.prodemundial.config.AuthenticatedUserResolver;
import ar.org.cpcemza.prodemundial.dto.CambiarPasswordRequestDTO;
import ar.org.cpcemza.prodemundial.model.Usuario;
import ar.org.cpcemza.prodemundial.service.PerfilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService             perfilService;
    private final AuthenticatedUserResolver userResolver;

    /**
     * PUT /api/perfil/cambiar-password
     *
     * Permite a cualquier usuario autenticado cambiar su propia contraseña.
     * Requiere la contraseña actual para confirmar identidad.
     *
     * Body:
     * {
     *   "passwordActual": "secreto123",
     *   "nuevaPassword":  "nuevoSecreto456"
     * }
     *
     * Respuestas:
     *   200 OK                 → contraseña actualizada
     *   400 Bad Request        → validación fallida (campos vacíos, menos de 6 chars)
     *   401 Unauthorized       → passwordActual incorrecta
     *   422 Unprocessable      → nueva contraseña igual a la actual
     */
    @PutMapping("/cambiar-password")
    public ResponseEntity<Map<String, String>> cambiarPassword(
            @Valid @RequestBody CambiarPasswordRequestDTO dto
    ) {
        Usuario usuario = userResolver.getUsuarioActual();

        perfilService.cambiarPassword(
                usuario,
                dto.getPasswordActual(),
                dto.getNuevaPassword()
        );

        return ResponseEntity.ok(Map.of(
                "mensaje", "Contraseña actualizada correctamente."
        ));
    }
}
