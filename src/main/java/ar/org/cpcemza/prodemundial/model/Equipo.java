package ar.org.cpcemza.prodemundial.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "equipos")
@Data
@NoArgsConstructor
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String grupo;

    @Column(name = "bandera_url")
    private String banderaUrl;

    @OneToOne(mappedBy = "equipo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private EstadisticaEquipo estadistica;
}
