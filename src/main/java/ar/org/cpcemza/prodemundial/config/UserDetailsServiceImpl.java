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
        ar.org.cpcemza.prodemundial.model.Usuario u = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        // --- INICIO DE AUDITORÍA FORENSE ---
        System.out.println("\n========== AUDITORÍA DE LOGIN ==========");
        System.out.println("1. Frontend busca el email: [" + email + "]");
        System.out.println("2. Base de datos devolvió usuario: [" + u.getNombre() + "]");
        System.out.println("3. Hash recuperado de Neon: [" + u.getPasswordHash() + "]");
        System.out.println("4. Longitud del Hash (Debe ser 60): " +
                (u.getPasswordHash() != null ? u.getPasswordHash().length() : "ES NULL!"));
        System.out.println("==========================================\n");
        // --- FIN DE AUDITORÍA ---

        return new User(
                u.getEmail(),
                u.getPasswordHash(),
                List.of(new SimpleGrantedAuthority(u.getRol()))
        );
    }
}