package ar.org.cpcemza.prodemundial.service;

import ar.org.cpcemza.prodemundial.exception.ResourceNotFoundException;
import ar.org.cpcemza.prodemundial.model.Partido;
import ar.org.cpcemza.prodemundial.model.Partido.EstadoPartido;
import ar.org.cpcemza.prodemundial.model.Prediccion;
import ar.org.cpcemza.prodemundial.model.Usuario;
import ar.org.cpcemza.prodemundial.repository.PartidoRepository;
import ar.org.cpcemza.prodemundial.repository.PrediccionRepository;
import ar.org.cpcemza.prodemundial.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculoPuntosService {

    private final PrediccionRepository prediccionRepository;
    private final UsuarioRepository    usuarioRepository;
    private final PartidoRepository    partidoRepository;

    @Transactional
    public void procesarResultados(Long partidoId, int golesLocalReal, int golesVisitanteReal) {
        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Partido", partidoId));

        if (partido.getEstado() == EstadoPartido.FINALIZADO) {
            throw new IllegalStateException("Este partido ya fue procesado.");
        }

        partido.setGolesLocal(golesLocalReal);
        partido.setGolesVisitante(golesVisitanteReal);
        partido.setEstado(EstadoPartido.FINALIZADO);
        partidoRepository.save(partido);

        List<Prediccion> predicciones = prediccionRepository.findByPartidoConUsuario(partido);
        if (predicciones.isEmpty()) {
            log.info("Partido id={} finalizado sin predicciones.", partidoId);
            return;
        }

        // Calcular puntos con UPDATE directo — sin N+1
        predicciones.forEach(p -> {
            int pts = calcularPuntos(golesLocalReal, golesVisitanteReal,
                                     p.getGolesLocal(), p.getGolesVisitante());
            prediccionRepository.updatePuntosById(p.getId(), pts);
        });

        // Actualizar totales de cada usuario afectado (una sola vez por usuario)
        Map<Usuario, List<Prediccion>> porUsuario = predicciones.stream()
                .collect(Collectors.groupingBy(Prediccion::getUsuario));

        porUsuario.forEach((usuario, preds) -> {
            usuario.setPuntosTotales(prediccionRepository.sumPuntosByUsuario(usuario));
            usuario.setPlenosTotales(prediccionRepository.countPlenos(usuario));
            usuarioRepository.save(usuario);
        });

        log.info("Partido id={} procesado. {} predicciones evaluadas.", partidoId, predicciones.size());
    }

    // Método público y estático → testeable sin mocks
    public static int calcularPuntos(int localReal, int visitaReal, int localPred, int visitaPred) {
        if (localReal == localPred && visitaReal == visitaPred) return 3;
        int tendReal = Integer.compare(localReal, visitaReal);
        int tendPred = Integer.compare(localPred, visitaPred);
        return tendReal == tendPred ? 1 : 0;
    }
}
