package ar.org.cpcemza.prodemundial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Lo que devuelve GET /api/equipos
 * El frontend usa: id, nombre, grupo, banderaUrl
 */
@Data
@AllArgsConstructor
public class EquipoResponseDTO {
    private Long    id;
    private String  nombre;
    private String  grupo;
    private String  banderaUrl;
    // Stats del torneo — calculadas en el service a partir de partidos finalizados
    private Integer pj;   // partidos jugados
    private Integer pg;   // ganados
    private Integer pe;   // empatados
    private Integer pp;   // perdidos
    private Integer gf;   // goles a favor
    private Integer gc;   // goles en contra
    private Integer pts;  // puntos
    // Info extra para la vista de detalle
    private Integer rankFifa;
    private Integer titulosMundiales;
}
