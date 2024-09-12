package fr.dawan.jeanpetebonbon.auth;

import fr.dawan.jeanpetebonbon.auth.dtos.LoginResponseDto;
import fr.dawan.jeanpetebonbon.auth.dtos.RegisterDto;
import fr.dawan.jeanpetebonbon.auth.dtos.LoginDto;
import org.springframework.security.core.AuthenticationException;

public interface AuthService {
  LoginResponseDto.UserDto register(RegisterDto register);

  LoginResponseDto authenticate(LoginDto login) throws AuthenticationException;
}
