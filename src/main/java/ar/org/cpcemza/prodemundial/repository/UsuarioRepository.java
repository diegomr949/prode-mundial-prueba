package ar.org.cpcemza.prodemundial.repository;

import ar.org.cpcemza.prodemundial.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Usado por: UserDetailsServiceImpl, AuthenticatedUserResolver, AuthService
    Optional<Usuario> findByEmail(String email);

    // Usado por: AuthService al registrar
    boolean existsByEmail(String email);

    // Usado por: AdminService — ranking general
    // Desempate: más plenos → registro más antiguo
    List<Usuario> findAllByOrderByPuntosTotalesDescPlenosTotalesDescFechaRegistroAsc();

    // Usado por: AdminService — ranking filtrado por área
    List<Usuario> findByAreaIgnoreCaseOrderByPuntosTotalesDescPlenosTotalesDescFechaRegistroAsc(
            String area
    );

    // Usado por: AdminService — lista de áreas para el filtro del ranking
    @Query("SELECT DISTINCT u.area FROM Usuario u WHERE u.area IS NOT NULL ORDER BY u.area ASC")
    List<String> findDistinctAreas();
}