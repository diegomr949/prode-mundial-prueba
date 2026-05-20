package ar.org.cpcemza.prodemundial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PrediccionResponseDTO {
    private Long          id;
    private Long          partidoId;
    private String        equipoLocal;
    private String        equipoVisitante;
    private Integer       golesLocalPredichos;
    private Integer       golesVisitantePredichos;
    private Integer       puntosObtenidos;
    private LocalDateTime fechaCarga;
    private boolean       bloqueada;
}
