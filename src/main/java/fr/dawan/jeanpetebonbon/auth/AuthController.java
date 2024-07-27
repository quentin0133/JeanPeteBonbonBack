package fr.dawan.jeanpetebonbon.auth;

import fr.dawan.jeanpetebonbon.auth.dtos.LoginDto;
import fr.dawan.jeanpetebonbon.auth.dtos.LoginResponseDto;
import fr.dawan.jeanpetebonbon.auth.dtos.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginDto login) throws Exception {
        return ResponseEntity.ok(authService.authenticate(login));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<LoginResponseDto.UserDto> register(@RequestBody RegisterDto register) {
        return ResponseEntity.ok(authService.register(register));
    }
}
