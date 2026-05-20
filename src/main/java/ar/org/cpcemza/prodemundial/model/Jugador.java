package ar.org.cpcemza.prodemundial.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jugadores")
@Data
@NoArgsConstructor
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Posicion posicion;

    @Column(name = "nro_camiseta")
    private Integer nroCamiseta;

    @Column(name = "es_estrella", nullable = false)
    private Boolean esEstrella = false;

    public enum Posicion {
        PORTERO, DEFENSA, MEDIOCAMPO, DELANTERO
    }
}
