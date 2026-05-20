package ar.org.cpcemza.prodemundial.service;

import ar.org.cpcemza.prodemundial.model.Usuario;
import ar.org.cpcemza.prodemundial.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerfilService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder   passwordEncoder;

    /**
     * Cambia la contraseña del usuario autenticado.
     *
     * Diferencia clave con el reset de admin:
     *  - Aquí se exige la contraseña ACTUAL para confirmar identidad.
     *  - El admin puede resetear sin conocer la actual (con ROLE_ADMIN).
     *
     * @param usuario       el usuario autenticado (viene del SecurityContext)
     * @param passwordActual contraseña que el usuario dice tener actualmente
     * @param nuevaPassword  nueva contraseña deseada
     * @throws IllegalArgumentException si la contraseña actual es incorrecta
     *                                  o si la nueva es igual a la actual
     */
    @Transactional
    public void cambiarPassword(Usuario usuario, String passwordActual, String nuevaPassword) {

        // 1. Verificar que la contraseña actual sea correcta
        if (!passwordEncoder.matches(passwordActual, usuario.getPasswordHash())) {
            // Mensaje genérico intencionalmente — misma respuesta que login incorrecto
            throw new IllegalArgumentException("La contraseña actual es incorrecta.");
        }

        // 2. No permitir cambiar a la misma contraseña
        if (passwordEncoder.matches(nuevaPassword, usuario.getPasswordHash())) {
            throw new IllegalArgumentException(
                "La nueva contraseña no puede ser igual a la contraseña actual."
            );
        }

        // 3. Validación extra de complejidad mínima (el DTO ya valida longitud >= 6)
        if (nuevaPassword.isBlank()) {
            throw new IllegalArgumentException("La nueva contraseña no puede estar vacía.");
        }

        // 4. Hashear y guardar
        usuario.setPasswordHash(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(usuario);

        log.info("[Perfil] Contraseña actualizada para usuario id={}", usuario.getId());
    }
}
