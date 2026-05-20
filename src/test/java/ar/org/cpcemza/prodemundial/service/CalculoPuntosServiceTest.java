package ar.org.cpcemza.prodemundial.service;

import ar.org.cpcemza.prodemundial.model.Partido;
import ar.org.cpcemza.prodemundial.model.Partido.EstadoPartido;
import ar.org.cpcemza.prodemundial.model.Prediccion;
import ar.org.cpcemza.prodemundial.model.Usuario;
import ar.org.cpcemza.prodemundial.repository.PartidoRepository;
import ar.org.cpcemza.prodemundial.repository.PrediccionRepository;
import ar.org.cpcemza.prodemundial.repository.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("CalculoPuntosService — Motor de puntos del Prode")
class CalculoPuntosServiceTest {

    @Mock private PrediccionRepository prediccionRepository;
    @Mock private UsuarioRepository    usuarioRepository;
    @Mock private PartidoRepository    partidoRepository;

    @InjectMocks
    private CalculoPuntosService service;

    /* ═══════════════════════════════════════════════════════
       TESTS UNITARIOS del método estático calcularPuntos()
       No necesitan mocks — pura lógica de negocio.
    ═══════════════════════════════════════════════════════ */
    @Nested
    @DisplayName("calcularPuntos() — reglas de negocio")
    class CalcularPuntosTests {

        @Test
        @DisplayName("3 puntos cuando el resultado exacto coincide — local gana")
        void pleno_localGana() {
            Assertions.assertThat(CalculoPuntosService.calcularPuntos(2, 1, 2, 1)).isEqualTo(3);
        }

        @Test
        @DisplayName("3 puntos cuando el resultado exacto coincide — visitante gana")
        void pleno_visitanteGana() {
            Assertions.assertThat(CalculoPuntosService.calcularPuntos(0, 3, 0, 3)).isEqualTo(3);
        }

        @Test
        @DisplayName("3 puntos cuando el resultado exacto coincide — empate")
        void pleno_empate() {
            Assertions.assertThat(CalculoPuntosService.calcularPuntos(1, 1, 1, 1)).isEqualTo(3);
        }

        @Test
        @DisplayName("3 puntos con empate 0-0")
        void pleno_empate_ceroACero() {
            Assertions.assertThat(CalculoPuntosService.calcularPuntos(0, 0, 0, 0)).isEqualTo(3);
        }

        @Test
        @DisplayName("1 punto cuando acierta tendencia — predice victoria local, goles distintos")
        void tendencia_localGana_golesDistintos() {
            // Real: 3-1 | Predicción: 2-0 → ambos local gana → 1 pt
            Assertions.assertThat(CalculoPuntosService.calcularPuntos(3, 1, 2, 0)).isEqualTo(1);
        }

        @Test
        @DisplayName("1 punto cuando acierta tendencia — predice victoria visitante")
        void tendencia_visitanteGana() {
            // Real: 0-2 | Predicción: 1-3 → ambos visitante gana → 1 pt
            Assertions.assertThat(CalculoPuntosService.calcularPuntos(0, 2, 1, 3)).isEqualTo(1);
        }

        @Test
        @DisplayName("1 punto cuando acierta tendencia — predice empate con distintos goles")
        void tendencia_empate_golesDistintos() {
            // Real: 2-2 | Predicción: 1-1 → ambos empate → 1 pt
            Assertions.assertThat(CalculoPuntosService.calcularPuntos(2, 2, 1, 1)).isEqualTo(1);
        }

        @Test
        @DisplayName("0 puntos cuando no acierta ni resultado ni tendencia")
        void fallo_total() {
            // Real: 2-0 (local gana) | Predicción: 0-1 (visitante gana) → 0 pt
            Assertions.assertThat(CalculoPuntosService.calcularPuntos(2, 0, 0, 1)).isEqualTo(0);
        }

        @Test
        @DisplayName("0 puntos — predice empate pero gana el local")
        void fallo_predicoEmpateGanaLocal() {
            // Real: 1-0 | Predicción: 0-0 → 0 pt
            Assertions.assertThat(CalculoPuntosService.calcularPuntos(1, 0, 0, 0)).isEqualTo(0);
        }

        @Test
        @DisplayName("0 puntos — predice victoria local pero hay empate")
        void fallo_predicoLocalGanaHayEmpate() {
            // Real: 1-1 | Predicción: 2-0 → 0 pt
            Assertions.assertThat(CalculoPuntosService.calcularPuntos(1, 1, 2, 0)).isEqualTo(0);
        }

        @Test
        @DisplayName("0 puntos — predice visitante gana pero gana local")
        void fallo_predicoVisitanteGanaLocalGana() {
            Assertions.assertThat(CalculoPuntosService.calcularPuntos(3, 0, 0, 2)).isEqualTo(0);
        }

        // Test parametrizado para cubrir más combinaciones
        @ParameterizedTest(name = "Real {0}-{1} | Pred {2}-{3} → {4} pts")
        @CsvSource({
            // Plenos (3 pts)
            "2,1,2,1,3",
            "0,0,0,0,3",
            "3,3,3,3,3",
            "1,0,1,0,3",
            "0,4,0,4,3",
            // Tendencias (1 pt)
            "2,0,1,0,1",  // local gana, goles distintos
            "0,2,0,1,1",  // visitante gana, goles distintos
            "2,2,1,1,1",  // empate, goles distintos
            "3,1,4,2,1",  // local gana ambos, distinto marcador
            "0,3,0,1,1",  // visitante gana ambos
            // Fallos (0 pts)
            "1,0,0,0,0",  // local gana, predijo empate
            "1,1,2,0,0",  // empate, predijo local gana
            "2,0,0,2,0",  // local gana, predijo visitante gana
            "0,2,2,0,0",  // visitante gana, predijo local gana
            "0,1,1,1,0",  // visitante gana, predijo empate
        })
        @DisplayName("Tabla de combinaciones")
        void tablaParametrizada(int rl, int rv, int pl, int pv, int expected) {
            Assertions.assertThat(CalculoPuntosService.calcularPuntos(rl, rv, pl, pv)).isEqualTo(expected);
        }
    }

    /* ═══════════════════════════════════════════════════════
       TESTS DE INTEGRACIÓN del método procesarResultados()
    ═══════════════════════════════════════════════════════ */
    @Nested
    @DisplayName("procesarResultados() — flujo completo")
    class ProcesarResultadosTests {

        private Partido  partido;
        private Usuario  usuario1;
        private Usuario  usuario2;

        @BeforeEach
        void setup() {
            partido = new Partido();
            partido.setId(1L);
            partido.setEstado(EstadoPartido.PENDIENTE);
            partido.setGolesLocal(null);
            partido.setGolesVisitante(null);

            usuario1 = new Usuario();
            usuario1.setId(1L);
            usuario1.setNombre("Ana");
            usuario1.setPuntosTotales(0);
            usuario1.setPlenosTotales(0);

            usuario2 = new Usuario();
            usuario2.setId(2L);
            usuario2.setNombre("Carlos");
            usuario2.setPuntosTotales(5);
            usuario2.setPlenosTotales(1);
        }

        @Test
        @DisplayName("Distribuye 3 pts a usuario con pleno y 1 pt al que acertó tendencia")
        void distribuyePuntosCorrectamente() {
            // Usuario1 predijo 2-1 → pleno (real: 2-1)
            Prediccion p1 = prediccionConDatos(1L, usuario1, partido, 2, 1);
            // Usuario2 predijo 3-0 → tendencia local gana (real: 2-1)
            Prediccion p2 = prediccionConDatos(2L, usuario2, partido, 3, 0);

            Mockito.when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));
            Mockito.when(prediccionRepository.findByPartidoConUsuario(partido)).thenReturn(List.of(p1, p2));
            Mockito.when(prediccionRepository.sumPuntosByUsuario(usuario1)).thenReturn(3);
            Mockito.when(prediccionRepository.sumPuntosByUsuario(usuario2)).thenReturn(6);
            Mockito.when(prediccionRepository.countPlenos(usuario1)).thenReturn(1);
            Mockito.when(prediccionRepository.countPlenos(usuario2)).thenReturn(1);

            service.procesarResultados(1L, 2, 1);

            // Verificar que se guardaron los puntos correctos
            Mockito.verify(prediccionRepository).updatePuntosById(1L, 3); // pleno
            Mockito.verify(prediccionRepository).updatePuntosById(2L, 1); // tendencia

            // Verificar que se actualizaron los totales del usuario1
            Assertions.assertThat(usuario1.getPuntosTotales()).isEqualTo(3);
            Assertions.assertThat(usuario1.getPlenosTotales()).isEqualTo(1);

            // Verificar que el partido quedó FINALIZADO
            Assertions.assertThat(partido.getEstado()).isEqualTo(EstadoPartido.FINALIZADO);
            Assertions.assertThat(partido.getGolesLocal()).isEqualTo(2);
            Assertions.assertThat(partido.getGolesVisitante()).isEqualTo(1);
        }

        @Test
        @DisplayName("0 pts para usuario que no acertó ni tendencia")
        void ceropuntosParaFallo() {
            // Usuario predijo visitante gana, pero ganó el local
            Prediccion p = prediccionConDatos(3L, usuario1, partido, 0, 2);

            Mockito.when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));
            Mockito.when(prediccionRepository.findByPartidoConUsuario(partido)).thenReturn(List.of(p));
            Mockito.when(prediccionRepository.sumPuntosByUsuario(usuario1)).thenReturn(0);
            Mockito.when(prediccionRepository.countPlenos(usuario1)).thenReturn(0);

            service.procesarResultados(1L, 2, 0);

            Mockito.verify(prediccionRepository).updatePuntosById(3L, 0);
            Assertions.assertThat(usuario1.getPuntosTotales()).isEqualTo(0);
            Assertions.assertThat(usuario1.getPlenosTotales()).isEqualTo(0);
        }

        @Test
        @DisplayName("Partido sin predicciones se finaliza sin errores")
        void sinPrediccionesNoFalla() {
            Mockito.when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));
            Mockito.when(prediccionRepository.findByPartidoConUsuario(partido)).thenReturn(List.of());

            Assertions.assertThatNoException().isThrownBy(() ->
                service.procesarResultados(1L, 1, 0)
            );

            Assertions.assertThat(partido.getEstado()).isEqualTo(EstadoPartido.FINALIZADO);
            Mockito.verify(prediccionRepository, Mockito.never()).updatePuntosById(ArgumentMatchers.any(), ArgumentMatchers.anyInt());
        }

        @Test
        @DisplayName("Lanza IllegalStateException si el partido ya fue procesado")
        void noReprocesaPartidoFinalizado() {
            partido.setEstado(EstadoPartido.FINALIZADO);
            Mockito.when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));

            Assertions.assertThatThrownBy(() -> service.procesarResultados(1L, 2, 1))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("ya fue procesado");
        }

        @Test
        @DisplayName("Lanza ResourceNotFoundException si el partido no existe")
        void lanzaExcepcionPartidoNoEncontrado() {
            Mockito.when(partidoRepository.findById(99L)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> service.procesarResultados(99L, 1, 0))
                    .isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("Acumula puntos correctamente sobre puntos previos del usuario")
        void acumulaPuntosCorrectamente() {
            // usuario2 ya tiene 5 pts y 1 pleno
            Prediccion p = prediccionConDatos(4L, usuario2, partido, 2, 0);

            Mockito.when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));
            Mockito.when(prediccionRepository.findByPartidoConUsuario(partido)).thenReturn(List.of(p));
            // sumPuntos devuelve el total NUEVO desde la BD (ya incluye el partido actual)
            Mockito.when(prediccionRepository.sumPuntosByUsuario(usuario2)).thenReturn(8); // 5 + 3
            Mockito.when(prediccionRepository.countPlenos(usuario2)).thenReturn(2);        // 1 + 1

            service.procesarResultados(1L, 2, 0);

            Assertions.assertThat(usuario2.getPuntosTotales()).isEqualTo(8);
            Assertions.assertThat(usuario2.getPlenosTotales()).isEqualTo(2);
            Mockito.verify(usuarioRepository).save(usuario2);
        }
    }

    /* ═══════════════════════════════════════════════════════
       TESTS del PerfilService
    ═══════════════════════════════════════════════════════ */
    @Nested
    @DisplayName("PerfilService — cambio de contraseña")
    class PerfilServiceTests {

        @Mock private UsuarioRepository    usuarioRepo;
        @Mock private org.springframework.security.crypto.password.PasswordEncoder encoder;

        private PerfilService perfilService;
        private Usuario       usuario;

        @BeforeEach
        void setup() {
            perfilService = new PerfilService(usuarioRepo, encoder);
            usuario = new Usuario();
            usuario.setId(1L);
            usuario.setPasswordHash("$2a$12$hashedPassword");
        }

        @Test
        @DisplayName("Cambia la contraseña correctamente cuando la actual es válida")
        void cambiaPasswordExitoso() {
            Mockito.when(encoder.matches("actual123", usuario.getPasswordHash())).thenReturn(true);
            Mockito.when(encoder.matches("nueva456",  usuario.getPasswordHash())).thenReturn(false);
            Mockito.when(encoder.encode("nueva456")).thenReturn("$2a$12$newHash");

            Assertions.assertThatNoException().isThrownBy(() ->
                perfilService.cambiarPassword(usuario, "actual123", "nueva456")
            );

            Assertions.assertThat(usuario.getPasswordHash()).isEqualTo("$2a$12$newHash");
            Mockito.verify(usuarioRepo).save(usuario);
        }

        @Test
        @DisplayName("Lanza excepción si la contraseña actual es incorrecta")
        void rechazaPasswordActualIncorrecta() {
            Mockito.when(encoder.matches("incorrecta", usuario.getPasswordHash())).thenReturn(false);

            Assertions.assertThatThrownBy(() ->
                perfilService.cambiarPassword(usuario, "incorrecta", "nueva456")
            )
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("actual es incorrecta");

            Mockito.verify(usuarioRepo, Mockito.never()).save(ArgumentMatchers.any());
        }

        @Test
        @DisplayName("Lanza excepción si la nueva contraseña es igual a la actual")
        void rechazaMismaPassword() {
            Mockito.when(encoder.matches("misma123", usuario.getPasswordHash())).thenReturn(true);
            // La nueva también matchea (son iguales)
            Mockito.when(encoder.matches("misma123", usuario.getPasswordHash())).thenReturn(true);

            Assertions.assertThatThrownBy(() ->
                perfilService.cambiarPassword(usuario, "misma123", "misma123")
            )
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("no puede ser igual");
        }
    }

    /* ── Helpers ── */
    private Prediccion prediccionConDatos(Long id, Usuario u, Partido p, int gl, int gv) {
        Prediccion pred = new Prediccion();
        pred.setId(id);
        pred.setUsuario(u);
        pred.setPartido(p);
        pred.setGolesLocal(gl);
        pred.setGolesVisitante(gv);
        pred.setPuntosObtenidos(null);
        return pred;
    }
}
