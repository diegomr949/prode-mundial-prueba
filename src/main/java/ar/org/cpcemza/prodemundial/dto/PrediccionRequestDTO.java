// ═══ PrediccionRequestDTO.java ═══════════════════════════════
package ar.org.cpcemza.prodemundial.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PrediccionRequestDTO {
    @NotNull(message = "El id del partido es obligatorio")
    private Long partidoId;

    @NotNull(message = "Los goles del local son obligatorios")
    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer golesLocal;

    @NotNull(message = "Los goles del visitante son obligatorios")
    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer golesVisitante;
}
