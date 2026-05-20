package ar.org.cpcemza.prodemundial.service;

import ar.org.cpcemza.prodemundial.dto.PrediccionRequestDTO;
import ar.org.cpcemza.prodemundial.exception.PrediccionCerradaException;
import ar.org.cpcemza.prodemundial.model.Partido;
import ar.org.cpcemza.prodemundial.model.Partido.EstadoPartido;
import ar.org.cpcemza.prodemundial.model.Prediccion;
import ar.org.cpcemza.prodemundial.model.Usuario;
import ar.org.cpcemza.prodemundial.repository.PartidoRepository;
import ar.org.cpcemza.prodemundial.repository.PrediccionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@DisplayName("PrediccionService — Validaciones y bloqueo")
class PrediccionServiceTest {

    @Mock private PrediccionRepository prediccionRepository;
    @Mock private PartidoRepository    partidoRepository;

    @InjectMocks
    private PrediccionService prediccionService;

    private Usuario  usuario;
    private Partido  partido;

    @BeforeEach
    void setup() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Test User");

        partido = new Partido();
        partido.setId(1L);
        partido.setEstado(EstadoPartido.PENDIENTE);
        // Partido en 2 horas — debería poder apostarse
        partido.setFechaHora(LocalDateTime.now().plusHours(2));
    }

    @Test
    @DisplayName("Guarda una nueva predicción correctamente cuando el partido está abierto")
    void guardaPrediccionNueva() {
        PrediccionRequestDTO dto = dto(1L, 2, 1);
        Prediccion saved = new Prediccion();
        saved.setId(10L);
        saved.setGolesLocal(2);
        saved.setGolesVisitante(1);
        saved.setPartido(partido);
        saved.setUsuario(usuario);

        Mockito.when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));
        Mockito.when(prediccionRepository.findByUsuarioAndPartido(usuario, partido))
                .thenReturn(Optional.empty());
        Mockito.when(prediccionRepository.save(ArgumentMatchers.any())).thenReturn(saved);

        Assertions.assertThatNoException().isThrownBy(() ->
                prediccionService.guardarPrediccion(usuario, dto)
        );
        Mockito.verify(prediccionRepository).save(ArgumentMatchers.any(Prediccion.class));
    }

    @Test
    @DisplayName("Actualiza predicción existente (upsert) sin lanzar excepción")
    void actualizaPrediccionExistente() {
        PrediccionRequestDTO dto = dto(1L, 1, 1);
        Prediccion existente = new Prediccion();
        existente.setId(5L);
        existente.setGolesLocal(3);    // valor anterior
        existente.setGolesVisitante(0);
        existente.setPartido(partido);
        existente.setUsuario(usuario);

        Mockito.when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));
        Mockito.when(prediccionRepository.findByUsuarioAndPartido(usuario, partido))
                .thenReturn(Optional.of(existente));
        Mockito.when(prediccionRepository.save(ArgumentMatchers.any())).thenReturn(existente);

        prediccionService.guardarPrediccion(usuario, dto);

        // Verificar que se actualizaron los goles
        Assertions.assertThat(existente.getGolesLocal()).isEqualTo(1);
        Assertions.assertThat(existente.getGolesVisitante()).isEqualTo(1);
        Mockito.verify(prediccionRepository).save(existente);
    }

    @Test
    @DisplayName("Bloquea predicción 15 minutos antes del partido")
    void bloqueaPrediccionCercaDelInicio() {
        // Partido en 10 minutos → bloqueado
        partido.setFechaHora(LocalDateTime.now().plusMinutes(10));

        Mockito.when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));

        Assertions.assertThatThrownBy(() ->
                prediccionService.guardarPrediccion(usuario, dto(1L, 1, 0))
        )
        .isInstanceOf(PrediccionCerradaException.class);

        Mockito.verify(prediccionRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Permite predicción exactamente a 16 minutos del partido")
    void permitePrediccionJustAntesDeCierre() {
        // Partido en 16 minutos → justo abierto
        partido.setFechaHora(LocalDateTime.now().plusMinutes(16));
        Prediccion saved = prediccionMock();

        Mockito.when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));
        Mockito.when(prediccionRepository.findByUsuarioAndPartido(usuario, partido))
                .thenReturn(Optional.empty());
        Mockito.when(prediccionRepository.save(ArgumentMatchers.any())).thenReturn(saved);

        Assertions.assertThatNoException().isThrownBy(() ->
                prediccionService.guardarPrediccion(usuario, dto(1L, 2, 2))
        );
    }

    @Test
    @DisplayName("Bloquea predicción si el partido está EN_JUEGO")
    void bloqueaPartidoEnJuego() {
        partido.setEstado(EstadoPartido.EN_JUEGO);
        partido.setFechaHora(LocalDateTime.now().minusMinutes(30));

        Mockito.when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));

        Assertions.assertThatThrownBy(() ->
                prediccionService.guardarPrediccion(usuario, dto(1L, 1, 0))
        )
        .isInstanceOf(PrediccionCerradaException.class);
    }

    @Test
    @DisplayName("Bloquea predicción si el partido está FINALIZADO")
    void bloqueaPartidoFinalizado() {
        partido.setEstado(EstadoPartido.FINALIZADO);
        partido.setFechaHora(LocalDateTime.now().minusHours(3));

        Mockito.when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));

        Assertions.assertThatThrownBy(() ->
                prediccionService.guardarPrediccion(usuario, dto(1L, 0, 0))
        )
        .isInstanceOf(PrediccionCerradaException.class);
    }

    /* ── helpers ── */
    private PrediccionRequestDTO dto(Long partidoId, int gl, int gv) {
        PrediccionRequestDTO d = new PrediccionRequestDTO();
        d.setPartidoId(partidoId);
        d.setGolesLocal(gl);
        d.setGolesVisitante(gv);
        return d;
    }

    private Prediccion prediccionMock() {
        Prediccion p = new Prediccion();
        p.setId(1L);
        p.setPartido(partido);
        p.setUsuario(usuario);
        p.setGolesLocal(2);
        p.setGolesVisitante(2);
        return p;
    }
}
