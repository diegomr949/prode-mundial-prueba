package ar.org.cpcemza.prodemundial.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ActualizarAreaRequestDTO {

    /**
     * Nullable: si se manda null o vacío, limpia el área del usuario.
     */
    @Size(max = 100, message = "El área no puede superar los 100 caracteres")
    private String area;
}