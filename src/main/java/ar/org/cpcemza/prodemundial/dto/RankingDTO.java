// RankingDTO.java
package ar.org.cpcemza.prodemundial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RankingDTO {
    private Integer posicion;
    private String nombre;
    private Integer puntosTotales;
    private Integer plenosTotales;
}