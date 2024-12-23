package fr.dawan.jeanpetebonbon.core.config;

import fr.dawan.jeanpetebonbon.core.interceptor.ExceptionHandlerFilter;
import fr.dawan.jeanpetebonbon.core.interceptor.JwtAuthFilter;
import java.time.Duration;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  // Requête publique pour tout type de requête
  @Getter
  private static final String[] AUTHORIZED_URL =
      new String[] {"/auth/**", "/error/**", "/users/**", "/"};

  @Getter private static final int EXPIRATION_TIME_SECONDS = 60 * 60 * 10;

  @Getter private static String SECRET_KEY;
  private final JwtAuthFilter jwtAuthFilter;
  private final ExceptionHandlerFilter exceptionHandlerFilter;
  private final UserDetailsService userDetailsService;

  @Value("${front.app.url}")
  private String frontUrl;

  @Value("${secret.key}")
  public void setSecretKey(String secretKey) {
    SECRET_KEY = secretKey;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.cors(
            cors ->
                cors.configurationSource(
                    request -> {
                      var corsConfiguration = new CorsConfiguration();
                      corsConfiguration.setAllowedOrigins(List.of(frontUrl));
                      corsConfiguration.setAllowedMethods(
                          List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                      corsConfiguration.setAllowedHeaders(List.of("*"));
                      corsConfiguration.setAllowCredentials(true);
                      corsConfiguration.setMaxAge(Duration.ofSeconds(EXPIRATION_TIME_SECONDS));
                      return corsConfiguration;
                    }))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(HttpMethod.OPTIONS, "/**")
                    .permitAll()
                    .requestMatchers(AUTHORIZED_URL)
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(jwtAuthFilter, ExceptionHandlerFilter.class)
        .userDetailsService(userDetailsService)
        .build();
  }

  @Bean
  public WebMvcConfigurer myMvcConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOrigins(frontUrl)
            .allowedMethods("*", "GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(EXPIRATION_TIME_SECONDS);
      }
    };
  }
}
