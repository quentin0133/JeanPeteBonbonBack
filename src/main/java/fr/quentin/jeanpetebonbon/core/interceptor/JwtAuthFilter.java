package fr.quentin.jeanpetebonbon.core.interceptor;

import fr.quentin.jeanpetebonbon.core.config.SecurityConfig;
import fr.quentin.jeanpetebonbon.core.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * The type Jwt auth filter.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret.key}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!request.getMethod().equals("OPTIONS") && isInterceptedRequest(request)) {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.toLowerCase().startsWith("bearer"))
                throw new ServletException("Invalid authorization");
            String jwtToken = authHeader.substring(7);
            String username = JwtUtils.extractUsername(jwtToken, jwtSecret);
            if (username != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (JwtUtils.validateToken(jwtToken, jwtSecret, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, jwtToken, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                else {
                    throw new ServletException("Invalid token");
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isInterceptedRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return Stream.concat(
                Arrays.stream(SecurityConfig.getAUTHORIZED_URLS()),
                Arrays.stream(SecurityConfig.getAUTHORIZED_URLS_BY_METHOD().getOrDefault(HttpMethod.valueOf(request.getMethod()), new String[0]))
            )
            .map(s -> s.replace("**", ".*"))
            .map(s -> s.replace("/.*", ".*"))
            .noneMatch(uri::matches);
    }
}
