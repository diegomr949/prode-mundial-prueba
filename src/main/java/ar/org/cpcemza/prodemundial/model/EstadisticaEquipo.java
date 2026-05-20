package ar.org.cpcemza.prodemundial.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estadisticas_equipo")
@Data
@NoArgsConstructor
public class EstadisticaEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false, unique = true)
    private Equipo equipo;

    @Column(name = "rank_fifa")
    private Integer rankFifa;

    @Column(name = "titulos_mundiales", nullable = false)
    private Integer titulosMundiales = 0;

    @Column(nullable = false) private Integer pj = 0;
    @Column(nullable = false) private Integer pg = 0;
    @Column(nullable = false) private Integer pe = 0;
    @Column(nullable = false) private Integer pp = 0;
    @Column(nullable = false) private Integer gf = 0;
    @Column(nullable = false) private Integer gc = 0;
}
