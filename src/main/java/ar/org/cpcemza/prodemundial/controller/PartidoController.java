package ar.org.cpcemza.prodemundial.controller;

import ar.org.cpcemza.prodemundial.config.AuthenticatedUserResolver;
import ar.org.cpcemza.prodemundial.dto.PartidoResponseDTO;
import ar.org.cpcemza.prodemundial.model.Partido.EstadoPartido;
import ar.org.cpcemza.prodemundial.service.PartidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class PartidoController {

    private final PartidoService           partidoService;
    private final AuthenticatedUserResolver userResolver;

    /**
     * GET /api/partidos
     * GET /api/partidos?estado=PENDIENTE
     * GET /api/partidos?estado=EN_JUEGO
     * GET /api/partidos?estado=FINALIZADO
     *
     * Devuelve el fixture completo con la predicción del usuario
     * autenticado incluida en cada partido (null si no cargó).
     */
    @GetMapping
    public ResponseEntity<List<PartidoResponseDTO>> getPartidos(
            @RequestParam(required = false) EstadoPartido estado
    ) {
        return ResponseEntity.ok(
                partidoService.getPartidos(estado, userResolver.getUsuarioActual())
        );
    }

    /**
     * GET /api/partidos/{id}
     * Detalle de un partido específico con la predicción del usuario.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartidoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                partidoService.getById(id, userResolver.getUsuarioActual())
        );
    }
}
