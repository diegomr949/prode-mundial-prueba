package ar.org.cpcemza.prodemundial.config;

import ar.org.cpcemza.prodemundial.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailIgnoreCase(email)
                .map(u -> new User(
                        u.getEmail(),
                        u.getPasswordHash(),
                        List.of(new SimpleGrantedAuthority(u.getRol()))
                ))
                // 👈 REPLICA ESTA LÍNEA REAL (Lanza la excepción nativa de Spring Security)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }
}
