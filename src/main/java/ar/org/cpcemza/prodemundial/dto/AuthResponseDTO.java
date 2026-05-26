package ar.org.cpcemza.prodemundial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Respuesta de login — sin token JWT.
 * El frontend recibe los datos del usuario y la sesión
 * queda activa via cookie JSESSIONID gestionada por el navegador.
 */
@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String  nombre;
    private String  email;
    private String  rol;
    private String  area;
}