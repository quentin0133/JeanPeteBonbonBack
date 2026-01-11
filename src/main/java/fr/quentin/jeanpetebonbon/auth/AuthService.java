package fr.quentin.jeanpetebonbon.auth;

import fr.quentin.jeanpetebonbon.auth.dtos.LoginCommandDto;
import fr.quentin.jeanpetebonbon.auth.dtos.LoginDto;
import org.springframework.security.core.AuthenticationException;

/**
 * The interface Auth service.
 */
public interface AuthService {
    /**
     * Authenticate login query dto.
     *
     * @param login the login
     * @return the login query dto
     * @throws AuthenticationException the authentication exception
     */
    LoginDto authenticate(LoginCommandDto login);
}
