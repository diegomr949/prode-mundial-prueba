package ar.org.cpcemza.prodemundial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String nombre;
    private String email;
    private String rol;
}
