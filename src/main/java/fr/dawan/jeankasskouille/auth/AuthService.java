package fr.dawan.jeankasskouille.auth;

import fr.dawan.jeankasskouille.auth.dtos.LoginResponseDto;
import fr.dawan.jeankasskouille.auth.dtos.RegisterDto;
import fr.dawan.jeankasskouille.auth.dtos.LoginDto;

public interface AuthService {
    LoginResponseDto.UserDto register(RegisterDto register);

    LoginResponseDto authenticate(LoginDto login) throws SecurityException;
}
