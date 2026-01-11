package fr.quentin.jeanpetebonbon.core.config;

import fr.quentin.jeanpetebonbon.core.interceptor.ExceptionHandlerFilter;
import fr.quentin.jeanpetebonbon.core.interceptor.JwtAuthFilter;
import fr.quentin.jeanpetebonbon.core.utils.JwtUtils;
import io.micrometer.common.lang.NonNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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

import java.util.List;
import java.util.Map;

/**
 * The type Security config.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    // Public request for any method
    @Getter
    private static final String[] AUTHORIZED_URLS = new String[]{
        "/v3/api-docs/**",
        "/swagger-ui/**"
    };

    // Public request by method
    @Getter
    private static final Map<HttpMethod, String[]> AUTHORIZED_URLS_BY_METHOD = Map.of(
        HttpMethod.GET, new String[]{
            "/files/**"
        },
        HttpMethod.POST, new String[]{
            "/api/auth/login"
        },
        HttpMethod.OPTIONS, new String[]{"/**"}
    );

    private final JwtAuthFilter jwtAuthFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final UserDetailsService userDetailsService;

    /**
     * Password encoder password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Authentication manager authentication manager.
     *
     * @param authenticationConfiguration the authentication configuration
     * @return the authentication manager
     * @throws Exception the exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Security filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(cors -> cors
                        .configurationSource(request -> {
                            var corsConfiguration = new CorsConfiguration();
                            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            corsConfiguration.setAllowedHeaders(List.of("*"));
                            corsConfiguration.setAllowCredentials(true);
                            corsConfiguration.setMaxAge(JwtUtils.TOKEN_DURATION);
                            return corsConfiguration;
                        })
                )
                .csrf(AbstractHttpConfigurer::disable) // NOSONAR
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(AUTHORIZED_URLS).permitAll()
                        .requestMatchers(HttpMethod.GET, AUTHORIZED_URLS_BY_METHOD.get(HttpMethod.GET)).permitAll()
                        .requestMatchers(HttpMethod.POST, AUTHORIZED_URLS_BY_METHOD.get(HttpMethod.POST)).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, AUTHORIZED_URLS_BY_METHOD.get(HttpMethod.OPTIONS)).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthFilter, ExceptionHandlerFilter.class)
                .userDetailsService(userDetailsService)
                .build();
    }

    /**
     * My mvc configurer web mvc configurer.
     *
     * @return the web mvc configurer
     */
    @Bean
    public WebMvcConfigurer myMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .allowedMethods("*", "GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .maxAge(JwtUtils.TOKEN_DURATION);
            }
        };
    }
}
