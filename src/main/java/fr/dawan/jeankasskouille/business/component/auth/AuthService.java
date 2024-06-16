package fr.dawan.jeankasskouille.business.component.auth;

import fr.dawan.jeankasskouille.business.component.auth.dtos.RegisterDto;
import fr.dawan.jeankasskouille.business.component.auth.dtos.LoginDto;
import fr.dawan.jeankasskouille.business.component.auth.dtos.LoginResponseDto;

public interface AuthService {
    LoginResponseDto.UserDto register(RegisterDto register);

    LoginResponseDto authenticate(LoginDto login) throws SecurityException;
}
