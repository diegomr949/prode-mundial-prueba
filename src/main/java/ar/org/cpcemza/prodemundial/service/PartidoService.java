package ar.org.cpcemza.prodemundial.service;

import ar.org.cpcemza.prodemundial.dto.PartidoResponseDTO;
import ar.org.cpcemza.prodemundial.exception.ResourceNotFoundException;
import ar.org.cpcemza.prodemundial.model.Partido;
import ar.org.cpcemza.prodemundial.model.Partido.EstadoPartido;
import ar.org.cpcemza.prodemundial.model.Prediccion;
import ar.org.cpcemza.prodemundial.model.Usuario;
import ar.org.cpcemza.prodemundial.repository.PartidoRepository;
import ar.org.cpcemza.prodemundial.repository.PrediccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartidoService {

    private final PartidoRepository    partidoRepository;
    private final PrediccionRepository prediccionRepository;

    /* ── GET todos o filtrados por estado ─────────────── */
    @Transactional(readOnly = true)
    public List<PartidoResponseDTO> getPartidos(EstadoPartido estado, Usuario usuario) {

        List<Partido> partidos = (estado != null)
                ? partidoRepository.findByEstadoOrderByFechaHoraAsc(estado)
                : partidoRepository.findAllByOrderByFechaHoraAsc();

        // Una sola query para las predicciones del usuario — evita N+1
        Map<Long, Prediccion> misPreds = prediccionRepository
                .findByUsuario(usuario)
                .stream()
                .collect(Collectors.toMap(p -> p.getPartido().getId(), p -> p));

        return partidos.stream()
                .map(p -> toDTO(p, misPreds.get(p.getId())))
                .collect(Collectors.toList());
    }

    /* ── GET un partido por id ─────────────────────────── */
    @Transactional(readOnly = true)
    public PartidoResponseDTO getById(Long id, Usuario usuario) {
        Partido p = partidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partido", id));

        Prediccion mia = prediccionRepository
                .findByUsuarioAndPartido(usuario, p)
                .orElse(null);

        return toDTO(p, mia);
    }

    /* ── Mapper entidad → DTO ───────────────────────────── */
    private PartidoResponseDTO toDTO(Partido p, Prediccion mia) {
        boolean bloqueado = p.getEstado() != EstadoPartido.PENDIENTE
                || LocalDateTime.now().isAfter(p.getFechaHora().minusMinutes(15));

        return new PartidoResponseDTO(
                p.getId(),
                p.getEquipoLocal().getNombre(),
                p.getEquipoLocal().getBanderaUrl(),
                p.getEquipoVisitante().getNombre(),
                p.getEquipoVisitante().getBanderaUrl(),
                p.getEquipoLocal().getGrupo(),
                p.getFechaHora(),
                p.getGolesLocal(),
                p.getGolesVisitante(),
                p.getEstado(),
                bloqueado,
                mia != null ? mia.getGolesLocal()        : null,
                mia != null ? mia.getGolesVisitante()    : null,
                mia != null ? mia.getPuntosObtenidos()   : null
        );
    }
}
