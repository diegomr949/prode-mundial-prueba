package ar.org.cpcemza.prodemundial.controller;

import ar.org.cpcemza.prodemundial.config.AuthenticatedUserResolver;
import ar.org.cpcemza.prodemundial.dto.PrediccionRequestDTO;
import ar.org.cpcemza.prodemundial.dto.PrediccionResponseDTO;
import ar.org.cpcemza.prodemundial.service.PrediccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predicciones")
@RequiredArgsConstructor
public class PrediccionController {

    private final PrediccionService        prediccionService;
    private final AuthenticatedUserResolver userResolver;

    /**
     * GET /api/predicciones/mis-predicciones
     * Devuelve todas las predicciones del usuario autenticado.
     */
    @GetMapping("/mis-predicciones")
    public ResponseEntity<List<PrediccionResponseDTO>> getMisPredicciones() {
        return ResponseEntity.ok(
                prediccionService.getMisPredicciones(userResolver.getUsuarioActual())
        );
    }

    /**
     * POST /api/predicciones
     * Crea o actualiza la predicción del usuario para un partido.
     * Si ya existe una predicción para ese partido, la sobreescribe
     * (siempre que el partido no esté bloqueado).
     *
     * Body:
     * {
     *   "partidoId":      1,
     *   "golesLocal":     2,
     *   "golesVisitante": 1
     * }
     */
    @PostMapping
    public ResponseEntity<PrediccionResponseDTO> guardar(
            @Valid @RequestBody PrediccionRequestDTO dto
    ) {
        return ResponseEntity.ok(
                prediccionService.guardarPrediccion(userResolver.getUsuarioActual(), dto)
        );
    }
}
