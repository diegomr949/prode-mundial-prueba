package ar.org.cpcemza.prodemundial.repository;

import ar.org.cpcemza.prodemundial.model.Partido;
import ar.org.cpcemza.prodemundial.model.Prediccion;
import ar.org.cpcemza.prodemundial.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrediccionRepository extends JpaRepository<Prediccion, Long> {

    List<Prediccion> findByUsuario(Usuario usuario);

    // JOIN FETCH evita N+1 al procesar resultados
    @Query("SELECT p FROM Prediccion p JOIN FETCH p.usuario JOIN FETCH p.partido WHERE p.partido = :partido")
    List<Prediccion> findByPartidoConUsuario(@Param("partido") Partido partido);

    Optional<Prediccion> findByUsuarioAndPartido(Usuario usuario, Partido partido);

    // Suma total de puntos — se usa para recalcular desde BD (fuente de verdad)
    @Query("SELECT COALESCE(SUM(p.puntosObtenidos), 0) FROM Prediccion p WHERE p.usuario = :usuario AND p.puntosObtenidos IS NOT NULL")
    Integer sumPuntosByUsuario(@Param("usuario") Usuario usuario);

    @Query("SELECT COUNT(p) FROM Prediccion p WHERE p.usuario = :usuario AND p.puntosObtenidos = 3")
    Integer countPlenos(@Param("usuario") Usuario usuario);

    int countByUsuario(Usuario usuario);

    // UPDATE directo sin cargar el objeto completo en memoria
    @Modifying
    @Query("UPDATE Prediccion p SET p.puntosObtenidos = :puntos WHERE p.id = :id")
    void updatePuntosById(@Param("id") Long id, @Param("puntos") int puntos);
}
