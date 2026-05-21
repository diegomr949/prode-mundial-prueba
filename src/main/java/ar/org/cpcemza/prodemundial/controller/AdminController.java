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

    /* ── Usuarios ─────────────────────────────────────── */

    @GetMapping("/usuarios")
    public ResponseEntity<List<DashboardUsuarioDTO>> getUsuarios() {
        return ResponseEntity.ok(adminService.getTodosLosUsuarios());
    }

    @GetMapping("/usuarios/{id}/dashboard")
    public ResponseEntity<DashboardUsuarioDTO> getDashboard(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getDashboardUsuario(id));
    }

    @PutMapping("/usuarios/{id}/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(
            @PathVariable Long id,
            @Valid @RequestBody ResetPasswordRequestDTO dto
    ) {
        adminService.resetPassword(id, dto.getNuevaPassword());
        return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada correctamente."));
    }

    /**
     * PUT /api/admin/usuarios/{id}/area
     * Asigna o corrige el área de un usuario.
     * Body: { "area": "Contabilidad" }
     * Para limpiarla: { "area": null } o { "area": "" }
     */
    @PutMapping("/usuarios/{id}/area")
    public ResponseEntity<Map<String, String>> actualizarArea(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarAreaRequestDTO dto
    ) {
        adminService.actualizarArea(id, dto.getArea());
        return ResponseEntity.ok(Map.of("mensaje", "Área actualizada correctamente."));
    }

    /**
     * GET /api/admin/areas
     * Lista todas las áreas distintas registradas.
     * Útil para cargar el dropdown de filtros en el frontend.
     */
    @GetMapping("/areas")
    public ResponseEntity<List<String>> getAreas() {
        return ResponseEntity.ok(adminService.getAreas());
    }

    /* ── Resultados ───────────────────────────────────── */

    @PutMapping("/partidos/{id}/resultado")
    public ResponseEntity<Map<String, String>> cargarResultado(
            @PathVariable Long id,
            @Valid @RequestBody ResultadoRequestDTO dto
    ) {
        calculoPuntosService.procesarResultados(id, dto.getGolesLocal(), dto.getGolesVisitante());
        return ResponseEntity.ok(Map.of("mensaje", "Resultado procesado y puntos calculados."));
    }

    /* ── Clasificación ────────────────────────────────── */

    /**
     * GET /api/admin/clasificacion
     * GET /api/admin/clasificacion?area=Contabilidad
     * Devuelve el ranking general o filtrado por área.
     */
    @GetMapping("/clasificacion")
    public ResponseEntity<List<ClasificacionDTO>> getClasificacion(
            @RequestParam(required = false) String area
    ) {
        List<ClasificacionDTO> result = (area != null && !area.isBlank())
                ? adminService.getClasificacionPorArea(area)
                : adminService.getClasificacion();
        return ResponseEntity.ok(result);
    }

    /* ── Jugadores ────────────────────────────────────── */

    @PostMapping("/equipos/{id}/jugadores")
    public ResponseEntity<JugadorResponseDTO> agregarJugador(
            @PathVariable Long id,
            @Valid @RequestBody JugadorRequestDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(equipoService.agregarJugador(id, dto));
    }

    @DeleteMapping("/jugadores/{id}")
    public ResponseEntity<Map<String, String>> eliminarJugador(@PathVariable Long id) {
        equipoService.eliminarJugador(id);
        return ResponseEntity.ok(Map.of("mensaje", "Jugador eliminado correctamente."));
    }
}