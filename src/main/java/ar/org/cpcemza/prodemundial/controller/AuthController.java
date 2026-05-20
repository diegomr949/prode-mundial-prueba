package ar.org.cpcemza.prodemundial.controller;

import ar.org.cpcemza.prodemundial.dto.AuthResponseDTO;
import ar.org.cpcemza.prodemundial.dto.LoginRequestDTO;
import ar.org.cpcemza.prodemundial.dto.RegistroRequestDTO;
import ar.org.cpcemza.prodemundial.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/registro")
    public ResponseEntity<AuthResponseDTO> registro(@Valid @RequestBody RegistroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registro(dto));
    }
}
