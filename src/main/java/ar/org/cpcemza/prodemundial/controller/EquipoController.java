package ar.org.cpcemza.prodemundial.controller;

import ar.org.cpcemza.prodemundial.dto.EquipoResponseDTO;
import ar.org.cpcemza.prodemundial.dto.JugadorRequestDTO;
import ar.org.cpcemza.prodemundial.dto.JugadorResponseDTO;
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
@RequiredArgsConstructor
public class EquipoController {

    private final EquipoService equipoService;

    /* ══════════════════════════════════════════════════════
       PÚBLICOS (cualquier usuario autenticado)
    ══════════════════════════════════════════════════════ */

    /**
     * GET /api/equipos
     * Lista todos los equipos con sus stats del torneo.
     * El frontend lo usa en la vista "Selecciones".
     */
    @GetMapping("/api/equipos")
    public ResponseEntity<List<EquipoResponseDTO>> getAll() {
        return ResponseEntity.ok(equipoService.getAll());
    }

    /**
     * GET /api/equipos/{id}
     * Detalle de un equipo específico con sus stats.
     */
    @GetMapping("/api/equipos/{id}")
    public ResponseEntity<EquipoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(equipoService.getById(id));
    }

    /**
     * GET /api/equipos/{id}/jugadores
     * Plantilla convocada de un equipo, ordenada por posición y número.
     * El frontend lo muestra en el modal de detalle de selección.
     */
    @GetMapping("/api/equipos/{id}/jugadores")
    public ResponseEntity<List<JugadorResponseDTO>> getJugadores(@PathVariable Long id) {
        return ResponseEntity.ok(equipoService.getJugadores(id));
    }

    /* ══════════════════════════════════════════════════════
       ADMIN — carga de plantillas
       Solo ROLE_ADMIN puede agregar / eliminar jugadores.
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
    @PostMapping("/api/admin/equipos/{id}/jugadores")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
    @DeleteMapping("/api/admin/jugadores/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> eliminarJugador(@PathVariable Long id) {
        equipoService.eliminarJugador(id);
        return ResponseEntity.ok(Map.of("mensaje", "Jugador eliminado correctamente."));
    }
}
