package ar.org.cpcemza.prodemundial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class DashboardUsuarioDTO {
    private Long                        id;
    private String                      nombre;
    private String                      email;
    private String                      rol;
    private String                      area;
    private Integer                     puntosTotales;
    private Integer                     plenosTotales;
    private LocalDateTime               fechaRegistro;
    private int                         partidosPredichos;
    private int                         partidosPendientes;
    private List<PrediccionResponseDTO> predicciones;
}