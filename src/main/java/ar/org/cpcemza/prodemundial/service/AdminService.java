package ar.org.cpcemza.prodemundial.service;

import ar.org.cpcemza.prodemundial.dto.ClasificacionDTO;
import ar.org.cpcemza.prodemundial.dto.DashboardUsuarioDTO;
import ar.org.cpcemza.prodemundial.dto.PrediccionResponseDTO;
import ar.org.cpcemza.prodemundial.exception.ResourceNotFoundException;
import ar.org.cpcemza.prodemundial.model.Partido.EstadoPartido;
import ar.org.cpcemza.prodemundial.model.Prediccion;
import ar.org.cpcemza.prodemundial.model.Usuario;
import ar.org.cpcemza.prodemundial.repository.PartidoRepository;
import ar.org.cpcemza.prodemundial.repository.PrediccionRepository;
import ar.org.cpcemza.prodemundial.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UsuarioRepository    usuarioRepository;
    private final PrediccionRepository prediccionRepository;
    private final PartidoRepository    partidoRepository;
    private final PasswordEncoder      passwordEncoder;

    /* ── Reset de contraseña por admin ─────────────────── */
    @Transactional
    public void resetPassword(Long usuarioId, String nuevaPassword) {
        Usuario u = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", usuarioId));
        u.setPasswordHash(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(u);
    }

    /* ── Dashboard de usuario ───────────────────────────── */
    @Transactional(readOnly = true)
    public DashboardUsuarioDTO getDashboardUsuario(Long usuarioId) {
        Usuario u = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", usuarioId));

        List<Prediccion> predicciones = prediccionRepository.findByUsuario(u);
        long total = partidoRepository.count();

        List<PrediccionResponseDTO> predsDTO = predicciones.stream()
                .map(p -> {
                    boolean bloqueada = p.getPartido().getEstado() != EstadoPartido.PENDIENTE
                            || LocalDateTime.now().isAfter(p.getPartido().getFechaHora().minusMinutes(15));
                    return new PrediccionResponseDTO(
                            p.getId(), p.getPartido().getId(),
                            p.getPartido().getEquipoLocal().getNombre(),
                            p.getPartido().getEquipoVisitante().getNombre(),
                            p.getGolesLocal(), p.getGolesVisitante(),
                            p.getPuntosObtenidos(), p.getFechaCarga(), bloqueada
                    );
                }).collect(Collectors.toList());

        return new DashboardUsuarioDTO(
                u.getId(), u.getNombre(), u.getEmail(), u.getRol(),
                u.getPuntosTotales(), u.getPlenosTotales(), u.getFechaRegistro(),
                predicciones.size(), (int)(total - predicciones.size()),
                predsDTO
        );
    }

    /* ── Lista todos los usuarios ───────────────────────── */
    @Transactional(readOnly = true)
    public List<DashboardUsuarioDTO> getTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(u -> getDashboardUsuario(u.getId()))
                .collect(Collectors.toList());
    }

    /* ── Clasificación general ──────────────────────────── */
    @Transactional(readOnly = true)
    public List<ClasificacionDTO> getClasificacion() {
        List<Usuario> usuarios = usuarioRepository
                .findAllByOrderByPuntosTotalesDescPlenosTotalesDescFechaRegistroAsc();
        long finalizados = partidoRepository.countByEstado(EstadoPartido.FINALIZADO);
        AtomicInteger pos = new AtomicInteger(1);

        return usuarios.stream().map(u -> {
            int predichos = prediccionRepository.countByUsuario(u);
            double pct = finalizados > 0
                    ? Math.round((double) u.getPlenosTotales() / finalizados * 1000.0) / 10.0
                    : 0.0;
            return new ClasificacionDTO(
                    pos.getAndIncrement(), u.getId(), u.getNombre(), u.getEmail(),
                    u.getPuntosTotales(), u.getPlenosTotales(), predichos, pct
            );
        }).collect(Collectors.toList());
    }
}
