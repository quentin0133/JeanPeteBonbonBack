package fr.quentin.jeanpetebonbon.auth;

import fr.quentin.jeanpetebonbon.auth.dtos.LoginCommandDto;
import fr.quentin.jeanpetebonbon.auth.dtos.LoginDto;
import fr.quentin.jeanpetebonbon.core.logs.LogController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    @LogController
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginDto> authenticate(@RequestBody LoginCommandDto login) {
        return ResponseEntity.ok(authService.authenticate(login));
    }
}
