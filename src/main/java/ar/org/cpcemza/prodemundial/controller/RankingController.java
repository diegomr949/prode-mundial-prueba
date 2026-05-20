package ar.org.cpcemza.prodemundial.controller;

import ar.org.cpcemza.prodemundial.dto.ClasificacionDTO;
import ar.org.cpcemza.prodemundial.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final AdminService adminService;

    /**
     * GET /api/ranking
     * Tabla de posiciones ordenada por puntos DESC, plenos DESC,
     * fecha de registro ASC (criterio de desempate).
     * Accesible para cualquier usuario autenticado.
     */
    @GetMapping
    public ResponseEntity<List<ClasificacionDTO>> getRanking() {
        return ResponseEntity.ok(adminService.getClasificacion());
    }
}
