package ar.org.cpcemza.prodemundial.repository;

import ar.org.cpcemza.prodemundial.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAllByOrderByPuntosTotalesDescPlenosTotalesDescFechaRegistroAsc();
    boolean existsByEmail(String email);
}
