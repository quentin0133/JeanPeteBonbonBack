package fr.dawan.jeanpetebonbon.auth;

import fr.dawan.jeanpetebonbon.auth.dtos.RegisterDto;
import fr.dawan.jeanpetebonbon.auth.dtos.LoginDto;
import fr.dawan.jeanpetebonbon.auth.dtos.LoginResponseDto;
import fr.dawan.jeanpetebonbon.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
  private final AuthMapper mapper;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public LoginResponseDto.UserDto register(RegisterDto register) {
    register.setPassword(passwordEncoder.encode(register.getPassword()));
    return mapper.toUserDto(userRepository.saveAndFlush(mapper.fromRegister(register)));
  }

  @Override
  public LoginResponseDto authenticate(LoginDto login) throws AuthenticationException {
    Authentication authenticate =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                login.email(), login.password(), new ArrayList<>()));
    if (authenticate.isAuthenticated()) {
      log.info(
          "Successful authentication for user {} at {}",
          login.email(),
          LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
      return mapper.toLoginResponse((UserSecurity) authenticate.getPrincipal());
    }
    throw new BadCredentialsException("Invalid Credentials");
  }
}
