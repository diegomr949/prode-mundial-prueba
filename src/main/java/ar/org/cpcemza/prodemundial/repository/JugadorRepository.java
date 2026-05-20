package ar.org.cpcemza.prodemundial.repository;

import ar.org.cpcemza.prodemundial.model.Equipo;
import ar.org.cpcemza.prodemundial.model.Jugador;
import ar.org.cpcemza.prodemundial.model.Jugador.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    // Traer plantilla ordenada por posición y luego por número de camiseta
    @Query("""
        SELECT j FROM Jugador j
        WHERE j.equipo = :equipo
        ORDER BY j.posicion ASC, j.nroCamiseta ASC NULLS LAST
        """)
    List<Jugador> findByEquipoOrdenado(@Param("equipo") Equipo equipo);

    // Solo las estrellas — para mostrar en tarjetas resumidas
    List<Jugador> findByEquipoAndEsEstrellaTrue(Equipo equipo);

    // Contar jugadores por equipo (para validar que la plantilla esté cargada)
    int countByEquipo(Equipo equipo);

    // Por posición (útil para filtros futuros)
    List<Jugador> findByEquipoAndPosicion(Equipo equipo, Posicion posicion);
}
