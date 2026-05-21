package ar.org.cpcemza.prodemundial.controller;

import ar.org.cpcemza.prodemundial.dto.ClasificacionDTO;
import ar.org.cpcemza.prodemundial.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final AdminService adminService;

    /**
     * GET /api/ranking               → ranking general
     * GET /api/ranking?area=Legales  → ranking solo del área "Legales"
     *
     * Accesible para cualquier usuario autenticado.
     */
    @GetMapping
    public ResponseEntity<List<ClasificacionDTO>> getRanking(
            @RequestParam(required = false) String area
    ) {
        List<ClasificacionDTO> result = (area != null && !area.isBlank())
                ? adminService.getClasificacionPorArea(area)
                : adminService.getClasificacion();
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/ranking/areas
     * Lista de áreas disponibles para mostrar en el filtro del frontend.
     */
    @GetMapping("/areas")
    public ResponseEntity<List<String>> getAreas() {
        return ResponseEntity.ok(adminService.getAreas());
    }
}