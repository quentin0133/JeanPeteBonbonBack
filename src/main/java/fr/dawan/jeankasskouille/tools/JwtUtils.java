package fr.dawan.jeankasskouille.tools;

import fr.dawan.jeankasskouille.config.SecurityConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtils {
    private static final long TOKEN_DURATION = Duration.ofSeconds(SecurityConfig.getEXPIRATION_TIME_SECONDS())
                                                    .toMillis(); // 1H
    private static final long REFRESH_TOKEN_DURATION = Duration.ofSeconds(SecurityConfig.getEXPIRATION_TIME_SECONDS() * 7L)
                                                            .toMillis(); // 1H

    public static String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public static boolean validateToken(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    public static boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).isBefore(LocalDate.now());
    }

    public static LocalDate extractExpiration(String jwtToken) {
        return convertToLocalDateViaInstant(extractClaim(jwtToken, Claims::getExpiration));
    }

    public static <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String jwtToken) {
        return Jwts.parser().setSigningKey(SecurityConfig.getSECRET_KEY()).parseClaimsJws(jwtToken).getBody();
    }

    private static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static String generateToken(UserDetails userDetails) {
        return createToken(userDetails, new Date(System.currentTimeMillis() + TOKEN_DURATION));
    }

    public static String generateRefreshToken(UserDetails userDetails) {
        return createToken(userDetails, new Date(System.currentTimeMillis() + REFRESH_TOKEN_DURATION));
    }

    private static String createToken(UserDetails userDetails, Date expiration) {

        return Jwts.builder().setClaims(getClaims(userDetails)).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SecurityConfig.getSECRET_KEY())
                .compact();
    }

    private static Map<String, Object> getClaims(UserDetails userDetails) {
        if (userDetails == null)
            throw new UsernameNotFoundException("User not found");
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        //Add other claim if needed
        return claims;
    }
}
