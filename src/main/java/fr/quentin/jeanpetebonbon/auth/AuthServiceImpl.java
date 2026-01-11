package fr.quentin.jeanpetebonbon.auth;

import fr.quentin.jeanpetebonbon.auth.dtos.LoginCommandDto;
import fr.quentin.jeanpetebonbon.auth.dtos.LoginDto;
import fr.quentin.jeanpetebonbon.auth.user.UserMapper;
import fr.quentin.jeanpetebonbon.auth.user.UserSecurity;
import fr.quentin.jeanpetebonbon.core.utils.JwtUtils;
import fr.quentin.jeanpetebonbon.core.utils.LogUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Override
    public LoginDto authenticate(LoginCommandDto login) throws AuthenticationException {
        LogUtils.logEnter(log, login);

        Authentication authenticate =
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        if (!authenticate.isAuthenticated())
            throw new BadCredentialsException("Invalid credentials");

        log.info(
            "Successful authentication for user {} at {}",
            login.getUsername(),
            LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
        );

        if (!(authenticate.getPrincipal() instanceof UserSecurity userSecurity))
            throw new IllegalStateException("Unexpected principal type");

        LoginDto loginDto = new LoginDto(userMapper.toDto(userSecurity), JwtUtils.generateToken(userSecurity, jwtSecretKey));

        LogUtils.logExit(log, loginDto);

        return loginDto;
    }
}
