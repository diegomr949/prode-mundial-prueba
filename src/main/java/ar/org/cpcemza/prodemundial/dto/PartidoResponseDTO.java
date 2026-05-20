package ar.org.cpcemza.prodemundial.dto;

import ar.org.cpcemza.prodemundial.model.Partido.EstadoPartido;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PartidoResponseDTO {
    private Long          id;
    private String        equipoLocal;
    private String        banderaLocal;
    private String        equipoVisitante;
    private String        banderaVisitante;
    private String        grupo;
    private LocalDateTime fechaHora;
    private Integer       golesLocal;
    private Integer       golesVisitante;
    private EstadoPartido estado;
    private boolean       prediccionBloqueada;
    // Predicción del usuario autenticado (null si no cargó)
    private Integer       miGolesLocal;
    private Integer       miGolesVisitante;
    private Integer       misPuntos;
}
