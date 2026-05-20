package ar.org.cpcemza.prodemundial.repository;

import ar.org.cpcemza.prodemundial.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    // Ordenar por grupo para que el frontend pueda agruparlos
    List<Equipo> findAllByOrderByGrupoAscNombreAsc();

    Optional<Equipo> findByNombre(String nombre);

    List<Equipo> findByGrupoOrderByNombreAsc(String grupo);

    // Traer equipo con su estadística en un solo JOIN — evita N+1
    @Query("""
        SELECT e FROM Equipo e
        LEFT JOIN FETCH e.estadistica
        WHERE e.id = :id
        """)
    Optional<Equipo> findByIdWithEstadistica(@Param("id") Long id);

    // Verificar si ya existe un equipo con ese nombre (para evitar duplicados)
    boolean existsByNombreIgnoreCase(String nombre);
}