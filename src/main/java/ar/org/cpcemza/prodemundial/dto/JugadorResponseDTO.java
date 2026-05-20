package ar.org.cpcemza.prodemundial.dto;

import ar.org.cpcemza.prodemundial.model.Jugador.Posicion;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Lo que devuelve GET /api/equipos/{id}/jugadores
 * El frontend usa: nroCamiseta, nombre, posicion, esEstrella
 */
@Data
@AllArgsConstructor
public class JugadorResponseDTO {
    private Long     id;
    private Integer  nroCamiseta;
    private String   nombre;
    private Posicion posicion;
    private Boolean  esEstrella;
}
