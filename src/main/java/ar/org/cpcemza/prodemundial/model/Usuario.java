package ar.org.cpcemza.prodemundial.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String nombre;

    /**
     * Área o sector del CPCE al que pertenece el usuario.
     * Nullable porque los usuarios cargados via SQL pueden no tenerla,
     * y el admin puede asignarla después.
     * Ejemplos: "Contabilidad", "Sistemas", "Presidencia", "Legales"
     */
    @Column(length = 100)
    private String area;

    @Column(name = "puntos_totales", columnDefinition = "integer default 0")
    private Integer puntosTotales = 0;

    @Column(name = "plenos_totales", columnDefinition = "integer default 0")
    private Integer plenosTotales = 0;

    @Column(nullable = false)
    private String rol = "ROLE_USER";

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
}