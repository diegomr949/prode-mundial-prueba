package ar.org.cpcemza.prodemundial.service;

import ar.org.cpcemza.prodemundial.dto.PrediccionRequestDTO;
import ar.org.cpcemza.prodemundial.dto.PrediccionResponseDTO;
import ar.org.cpcemza.prodemundial.exception.PrediccionCerradaException;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrediccionService {

    private final PrediccionRepository prediccionRepository;
    private final PartidoRepository    partidoRepository;

    @Transactional
    public PrediccionResponseDTO guardarPrediccion(Usuario usuario, PrediccionRequestDTO dto) {
        Partido partido = partidoRepository.findById(dto.getPartidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Partido", dto.getPartidoId()));

        // Validación 1: estado del partido
        if (partido.getEstado() != EstadoPartido.PENDIENTE) {
            throw new PrediccionCerradaException();
        }
        // Validación 2: corte 15 min antes del inicio
        if (LocalDateTime.now().isAfter(partido.getFechaHora().minusMinutes(15))) {
            throw new PrediccionCerradaException();
        }

        // Upsert: actualiza si ya existe, crea si no
        Prediccion prediccion = prediccionRepository
                .findByUsuarioAndPartido(usuario, partido)
                .orElse(new Prediccion());

        prediccion.setUsuario(usuario);
        prediccion.setPartido(partido);
        prediccion.setGolesLocal(dto.getGolesLocal());
        prediccion.setGolesVisitante(dto.getGolesVisitante());

        return toDTO(prediccionRepository.save(prediccion));
    }

    @Transactional(readOnly = true)
    public List<PrediccionResponseDTO> getMisPredicciones(Usuario usuario) {
        return prediccionRepository.findByUsuario(usuario)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private PrediccionResponseDTO toDTO(Prediccion p) {
        boolean bloqueada = p.getPartido().getEstado() != EstadoPartido.PENDIENTE
                || LocalDateTime.now().isAfter(p.getPartido().getFechaHora().minusMinutes(15));
        return new PrediccionResponseDTO(
                p.getId(), p.getPartido().getId(),
                p.getPartido().getEquipoLocal().getNombre(),
                p.getPartido().getEquipoVisitante().getNombre(),
                p.getGolesLocal(), p.getGolesVisitante(),
                p.getPuntosObtenidos(), p.getFechaCarga(), bloqueada
        );
    }
}
