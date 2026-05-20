package ar.org.cpcemza.prodemundial.service;

import ar.org.cpcemza.prodemundial.dto.EquipoResponseDTO;
import ar.org.cpcemza.prodemundial.dto.JugadorRequestDTO;
import ar.org.cpcemza.prodemundial.dto.JugadorResponseDTO;
import ar.org.cpcemza.prodemundial.exception.ResourceNotFoundException;
import ar.org.cpcemza.prodemundial.model.Equipo;
import ar.org.cpcemza.prodemundial.model.EstadisticaEquipo;
import ar.org.cpcemza.prodemundial.model.Jugador;
import ar.org.cpcemza.prodemundial.model.Partido;
import ar.org.cpcemza.prodemundial.model.Partido.EstadoPartido;
import ar.org.cpcemza.prodemundial.repository.EquipoRepository;
import ar.org.cpcemza.prodemundial.repository.JugadorRepository;
import ar.org.cpcemza.prodemundial.repository.PartidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipoService {

    private final EquipoRepository   equipoRepository;
    private final JugadorRepository  jugadorRepository;
    private final PartidoRepository  partidoRepository;

    /* ══════════════════════════════════════════════════════
       GET ALL EQUIPOS con stats del torneo calculadas
    ══════════════════════════════════════════════════════ */
    @Transactional(readOnly = true)
    public List<EquipoResponseDTO> getAll() {
        List<Equipo>  equipos  = equipoRepository.findAllByOrderByGrupoAscNombreAsc();
        // Traer todos los partidos finalizados de una sola query — evita N+1
        List<Partido> finalizados = partidoRepository.findByEstado(EstadoPartido.FINALIZADO);

        return equipos.stream()
                .map(e -> toDTO(e, calcularStats(e, finalizados)))
                .collect(Collectors.toList());
    }

    /* ══════════════════════════════════════════════════════
       GET EQUIPO por ID con stats
    ══════════════════════════════════════════════════════ */
    @Transactional(readOnly = true)
    public EquipoResponseDTO getById(Long id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo", id));

        List<Partido> finalizados = partidoRepository.findByEstado(EstadoPartido.FINALIZADO);
        return toDTO(equipo, calcularStats(equipo, finalizados));
    }

    /* ══════════════════════════════════════════════════════
       GET JUGADORES de un equipo
    ══════════════════════════════════════════════════════ */
    @Transactional(readOnly = true)
    public List<JugadorResponseDTO> getJugadores(Long equipoId) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo", equipoId));

        return jugadorRepository.findByEquipoOrdenado(equipo)
                .stream()
                .map(this::toJugadorDTO)
                .collect(Collectors.toList());
    }

    /* ══════════════════════════════════════════════════════
       ADMIN: agregar jugador a un equipo
    ══════════════════════════════════════════════════════ */
    @Transactional
    public JugadorResponseDTO agregarJugador(Long equipoId, JugadorRequestDTO dto) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo", equipoId));

        Jugador jugador = new Jugador();
        jugador.setEquipo(equipo);
        jugador.setNombre(dto.getNombre());
        jugador.setPosicion(dto.getPosicion());
        jugador.setNroCamiseta(dto.getNroCamiseta());
        jugador.setEsEstrella(dto.getEsEstrella() != null ? dto.getEsEstrella() : false);

        Jugador saved = jugadorRepository.save(jugador);
        log.info("Jugador agregado: {} → {}", saved.getNombre(), equipo.getNombre());
        return toJugadorDTO(saved);
    }

    /* ══════════════════════════════════════════════════════
       ADMIN: eliminar jugador
    ══════════════════════════════════════════════════════ */
    @Transactional
    public void eliminarJugador(Long jugadorId) {
        if (!jugadorRepository.existsById(jugadorId)) {
            throw new ResourceNotFoundException("Jugador", jugadorId);
        }
        jugadorRepository.deleteById(jugadorId);
    }

    /* ══════════════════════════════════════════════════════
       PRIVADOS
    ══════════════════════════════════════════════════════ */

    /**
     * Calcula las estadísticas de un equipo a partir de los partidos
     * ya finalizados. Se pasa la lista completa para evitar N+1 queries.
     */
    private int[] calcularStats(Equipo equipo, List<Partido> finalizados) {
        // [pj, pg, pe, pp, gf, gc, pts]
        int[] s = new int[7];

        finalizados.stream()
                .filter(p -> p.getEquipoLocal().getId().equals(equipo.getId())
                          || p.getEquipoVisitante().getId().equals(equipo.getId()))
                .forEach(p -> {
                    boolean esLocal = p.getEquipoLocal().getId().equals(equipo.getId());
                    int gf = esLocal ? (p.getGolesLocal()     != null ? p.getGolesLocal()     : 0)
                                     : (p.getGolesVisitante() != null ? p.getGolesVisitante() : 0);
                    int gc = esLocal ? (p.getGolesVisitante() != null ? p.getGolesVisitante() : 0)
                                     : (p.getGolesLocal()     != null ? p.getGolesLocal()     : 0);
                    s[0]++; // pj
                    s[4] += gf;
                    s[5] += gc;
                    if      (gf > gc) { s[1]++; s[6] += 3; }
                    else if (gf == gc){ s[2]++; s[6] += 1; }
                    else               { s[3]++; }
                });

        return s;
    }

    private EquipoResponseDTO toDTO(Equipo e, int[] s) {
        EstadisticaEquipo est = e.getEstadistica(); // puede ser null si no se cargó aún
        return new EquipoResponseDTO(
                e.getId(),
                e.getNombre(),
                e.getGrupo(),
                e.getBanderaUrl(),
                s[0], s[1], s[2], s[3], s[4], s[5], s[6],  // stats del torneo
                est != null ? est.getRankFifa()          : null,
                est != null ? est.getTitulosMundiales()  : null
        );
    }

    private JugadorResponseDTO toJugadorDTO(Jugador j) {
        return new JugadorResponseDTO(
                j.getId(),
                j.getNroCamiseta(),
                j.getNombre(),
                j.getPosicion(),
                j.getEsEstrella()
        );
    }
}
