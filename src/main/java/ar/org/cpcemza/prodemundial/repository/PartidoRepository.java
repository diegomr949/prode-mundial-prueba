package ar.org.cpcemza.prodemundial.repository;

import ar.org.cpcemza.prodemundial.model.Partido;
import ar.org.cpcemza.prodemundial.model.Partido.EstadoPartido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
    List<Partido> findAllByOrderByFechaHoraAsc();
    List<Partido> findByEstado(EstadoPartido estado);
    List<Partido> findByEstadoOrderByFechaHoraAsc(EstadoPartido estado);
    long countByEstado(EstadoPartido estado);
}
