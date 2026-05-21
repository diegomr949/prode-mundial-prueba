package ar.org.cpcemza.prodemundial.repository;

import ar.org.cpcemza.prodemundial.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    // Ranking general (desempate: más plenos → registro más antiguo)
    List<Usuario> findAllByOrderByPuntosTotalesDescPlenosTotalesDescFechaRegistroAsc();

    // Ranking filtrado por área — ignora mayúsculas/minúsculas
    List<Usuario> findByAreaIgnoreCaseOrderByPuntosTotalesDescPlenosTotalesDescFechaRegistroAsc(
            String area
    );

    // Lista de áreas distintas cargadas (para el filtro del ranking)
    @Query("SELECT DISTINCT u.area FROM Usuario u WHERE u.area IS NOT NULL ORDER BY u.area ASC")
    List<String> findDistinctAreas();
}