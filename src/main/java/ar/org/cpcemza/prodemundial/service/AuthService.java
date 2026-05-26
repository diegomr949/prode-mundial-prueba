package ar.org.cpcemza.prodemundial.service;

import ar.org.cpcemza.prodemundial.dto.AuthResponseDTO;
import ar.org.cpcemza.prodemundial.dto.CrearUsuarioRequestDTO;
import ar.org.cpcemza.prodemundial.dto.LoginRequestDTO;
import ar.org.cpcemza.prodemundial.model.Usuario;
import ar.org.cpcemza.prodemundial.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository     usuarioRepository;
    private final PasswordEncoder       passwordEncoder;
    private final AuthenticationManager authManager;

    /**
     * Login con sesión de servidor.
     * Spring guarda el SecurityContext en la sesión HTTP
     * y devuelve una cookie JSESSIONID al navegador.
     * No se genera ningún token JWT.
     */
    public AuthResponseDTO login(LoginRequestDTO dto) {
        // Autentica y guarda en el SecurityContext de la sesión
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        Usuario u = usuarioRepository.findByEmail(dto.getEmail()).orElseThrow();
        log.info("[Auth] Login exitoso: {}", u.getEmail());
        return toResponse(u);
    }

    /**
     * Devuelve los datos del usuario actualmente autenticado.
     * El frontend lo llama al cargar la app para verificar si hay sesión activa.
     */
    public AuthResponseDTO getMe(String email) {
        Usuario u = usuarioRepository.findByEmail(email).orElseThrow();
        return toResponse(u);
    }

    /**
     * Crea un usuario nuevo — solo lo puede invocar el admin.
     * El endpoint está en AdminController bajo /api/admin/usuarios.
     */
    @Transactional
    public AuthResponseDTO crearUsuario(CrearUsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setEmail(dto.getEmail());
        u.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        u.setRol("ROLE_USER");
        u.setArea(dto.getArea() != null && !dto.getArea().isBlank()
                ? dto.getArea().trim() : null);
        usuarioRepository.save(u);
        log.info("[Admin] Usuario creado: {}", u.getEmail());
        return toResponse(u);
    }

    private AuthResponseDTO toResponse(Usuario u) {
        return new AuthResponseDTO(
                u.getNombre(),
                u.getEmail(),
                u.getRol(),
                u.getArea()
        );
    }
}