package ar.org.cpcemza.prodemundial.config;

import ar.org.cpcemza.prodemundial.exception.ResourceNotFoundException;
import ar.org.cpcemza.prodemundial.model.Usuario;
import ar.org.cpcemza.prodemundial.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Extrae el Usuario autenticado del SecurityContext.
 * Usado en todos los controllers que necesitan el usuario actual.
 */
@Component
@RequiredArgsConstructor
public class AuthenticatedUserResolver {

    private final UsuarioRepository usuarioRepository;

    public Usuario getUsuarioActual() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario autenticado", 0L));
    }
}
