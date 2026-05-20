package ar.org.cpcemza.prodemundial.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResultadoRequestDTO {
    @NotNull
    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer golesLocal;

    @NotNull
    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer golesVisitante;
}
