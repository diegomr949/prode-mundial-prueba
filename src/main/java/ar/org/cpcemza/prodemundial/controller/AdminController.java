package ar.org.cpcemza.prodemundial.controller;

import ar.org.cpcemza.prodemundial.dto.*;
import ar.org.cpcemza.prodemundial.service.AdminService;
import ar.org.cpcemza.prodemundial.service.CalculoPuntosService;
import ar.org.cpcemza.prodemundial.service.EquipoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final AdminService         adminService;
    private final CalculoPuntosService calculoPuntosService;
    private final EquipoService        equipoService;

    /* ══════════════════════════════════════════════════════
       USUARIOS
    ══════════════════════════════════════════════════════ */

    /** GET /api/admin/usuarios — lista todos con sus stats */
    @GetMapping("/usuarios")
    public ResponseEntity<List<DashboardUsuarioDTO>> getUsuarios() {
        return ResponseEntity.ok(adminService.getTodosLosUsuarios());
    }

    /** GET /api/admin/usuarios/{id}/dashboard — detalle completo */
    @GetMapping("/usuarios/{id}/dashboard")
    public ResponseEntity<DashboardUsuarioDTO> getDashboard(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getDashboardUsuario(id));
    }

    /** PUT /api/admin/usuarios/{id}/reset-password */
    @PutMapping("/usuarios/{id}/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(
            @PathVariable Long id,
            @Valid @RequestBody ResetPasswordRequestDTO dto
    ) {
        adminService.resetPassword(id, dto.getNuevaPassword());
        return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada correctamente."));
    }

    /* ══════════════════════════════════════════════════════
       RESULTADOS DE PARTIDOS
    ══════════════════════════════════════════════════════ */

    /**
     * PUT /api/admin/partidos/{id}/resultado
     * Registra el resultado real y dispara el cálculo de puntos
     * para todos los usuarios que apostaron en ese partido.
     */
    @PutMapping("/partidos/{id}/resultado")
    public ResponseEntity<Map<String, String>> cargarResultado(
            @PathVariable Long id,
            @Valid @RequestBody ResultadoRequestDTO dto
    ) {
        calculoPuntosService.procesarResultados(id, dto.getGolesLocal(), dto.getGolesVisitante());
        return ResponseEntity.ok(Map.of("mensaje", "Resultado procesado y puntos calculados."));
    }

    /* ══════════════════════════════════════════════════════
       CLASIFICACIÓN (versión admin — incluye email)
    ══════════════════════════════════════════════════════ */

    /** GET /api/admin/clasificacion */
    @GetMapping("/clasificacion")
    public ResponseEntity<List<ClasificacionDTO>> getClasificacion() {
        return ResponseEntity.ok(adminService.getClasificacion());
    }

    /* ══════════════════════════════════════════════════════
       JUGADORES / PLANTILLAS
    ══════════════════════════════════════════════════════ */

    /**
     * POST /api/admin/equipos/{id}/jugadores
     * Agrega un jugador a la plantilla del equipo.
     *
     * Body ejemplo:
     * {
     *   "nombre":      "Lionel Messi",
     *   "posicion":    "DELANTERO",
     *   "nroCamiseta": 10,
     *   "esEstrella":  true
     * }
     */
    @PostMapping("/equipos/{id}/jugadores")
    public ResponseEntity<JugadorResponseDTO> agregarJugador(
            @PathVariable Long id,
            @Valid @RequestBody JugadorRequestDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(equipoService.agregarJugador(id, dto));
    }

    /**
     * DELETE /api/admin/jugadores/{id}
     * Elimina un jugador de la plantilla.
     */
    @DeleteMapping("/jugadores/{id}")
    public ResponseEntity<Map<String, String>> eliminarJugador(@PathVariable Long id) {
        equipoService.eliminarJugador(id);
        return ResponseEntity.ok(Map.of("mensaje", "Jugador eliminado correctamente."));
    }
}
