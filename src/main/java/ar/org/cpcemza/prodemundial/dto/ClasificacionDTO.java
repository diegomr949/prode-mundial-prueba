package ar.org.cpcemza.prodemundial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClasificacionDTO {
    private Integer posicion;
    private Long    usuarioId;
    private String  nombre;
    private String  email;
    private Integer puntosTotales;
    private Integer plenosTotales;
    private Integer partidosPredichos;
    private Double  porcentajeAciertos;
}
