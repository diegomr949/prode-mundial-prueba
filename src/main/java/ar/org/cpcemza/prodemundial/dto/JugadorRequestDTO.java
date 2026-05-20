package ar.org.cpcemza.prodemundial.dto;

import ar.org.cpcemza.prodemundial.model.Jugador.Posicion;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Body de POST /api/admin/equipos/{id}/jugadores
 */
@Data
public class JugadorRequestDTO {

    @NotBlank(message = "El nombre del jugador es obligatorio")
    private String nombre;

    @NotNull(message = "La posición es obligatoria")
    private Posicion posicion;

    @Min(value = 1,  message = "El número de camiseta debe ser entre 1 y 99")
    @Max(value = 99, message = "El número de camiseta debe ser entre 1 y 99")
    private Integer nroCamiseta;

    private Boolean esEstrella = false;
}
