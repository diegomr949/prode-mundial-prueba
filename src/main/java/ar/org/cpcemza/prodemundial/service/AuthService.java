package ar.org.cpcemza.prodemundial.service;

import ar.org.cpcemza.prodemundial.config.JwtUtil;
import ar.org.cpcemza.prodemundial.dto.AuthResponseDTO;
import ar.org.cpcemza.prodemundial.dto.LoginRequestDTO;
import ar.org.cpcemza.prodemundial.dto.RegistroRequestDTO;
import ar.org.cpcemza.prodemundial.exception.ResourceNotFoundException;
import ar.org.cpcemza.prodemundial.model.Usuario;
import ar.org.cpcemza.prodemundial.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository     usuarioRepository;
    private final PasswordEncoder       passwordEncoder;
    private final JwtUtil               jwtUtil;
    private final AuthenticationManager authManager;

    public AuthResponseDTO login(LoginRequestDTO dto) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        // 👈 REPLICA ESTA LÍNEA REAL (Usa tu manejador global de errores)
        Usuario u = usuarioRepository.findByEmailIgnoreCase(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", 0L));

        return toAuthResponse(u);
    }

    @Transactional
    public AuthResponseDTO registro(RegistroRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setEmail(dto.getEmail());
        u.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        u.setRol("ROLE_USER");
        // area es opcional — puede llegar null si el usuario no la completó
        u.setArea(dto.getArea() != null && !dto.getArea().isBlank()
                ? dto.getArea().trim()
                : null);
        usuarioRepository.save(u);
        return toAuthResponse(u);
    }

    private AuthResponseDTO toAuthResponse(Usuario u) {
        return new AuthResponseDTO(
                jwtUtil.generateToken(u.getEmail()),
                u.getNombre(),
                u.getEmail(),
                u.getRol(),
                u.getArea()    // null si no fue asignada
        );
    }
}