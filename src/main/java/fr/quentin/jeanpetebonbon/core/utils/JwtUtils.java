package fr.quentin.jeanpetebonbon.core.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The type Jwt utils.
 */
public class JwtUtils {
    public static final long TOKEN_DURATION = (long) 3600 * 1000 * 5; // 5 hours

    private JwtUtils() {
    }

    /**
     * Extract username string.
     *
     * @param jwtToken the jwt token
     * @return the string
     */
    public static String extractUsername(String jwtToken, String jwtSecretKey) {
        return extractClaim(jwtToken, jwtSecretKey, Claims::getSubject);
    }

    /**
     * Validate token boolean.
     *
     * @param jwtToken    the jwt token
     * @param userDetails the user details
     * @return the boolean
     */
    public static boolean validateToken(String jwtToken, String jwtSecretKey, UserDetails userDetails) {
        final String username = extractUsername(jwtToken, jwtSecretKey);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken, jwtSecretKey);
    }

    /**
     * Is token expired boolean.
     *
     * @param jwtToken the jwt token
     * @return the boolean
     */
    public static boolean isTokenExpired(String jwtToken, String jwtSecretKey) {
        return extractExpiration(jwtToken, jwtSecretKey).isBefore(LocalDate.now());
    }

    /**
     * Extract expiration local date.
     *
     * @param jwtToken the jwt token
     * @return the local date
     */
    public static LocalDate extractExpiration(String jwtToken, String jwtSecretKey) {
        return convertToLocalDateViaInstant(extractClaim(jwtToken, jwtSecretKey, Claims::getExpiration));
    }

    /**
     * Extract claim t.
     *
     * @param <T>            the type parameter
     * @param jwtToken       the jwt token
     * @param claimsResolver the claims resolver
     * @return the t
     */
    public static <T> T extractClaim(String jwtToken, String jwtSecretKey, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken, jwtSecretKey);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String jwtToken, String jwtSecretKey) {
        return Jwts.parser()
            .verifyWith(getSigningKey(jwtSecretKey))
            .build()
            .parseSignedClaims(jwtToken)
            .getPayload();
    }

    private static SecretKey getSigningKey(String jwtSecretKey) {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    private static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }

    /**
     * Generate token string.
     *
     * @param userDetails the user details
     * @return the string
     */
    public static String generateToken(UserDetails userDetails, String jwtSecretKey) {
        return createToken(userDetails, jwtSecretKey, new Date(System.currentTimeMillis() + TOKEN_DURATION));
    }

    private static String createToken(UserDetails userDetails, String jwtSecretKey, Date expiration) {

        return Jwts.builder()
            .claims(getClaims(userDetails))
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(expiration)
            .signWith(getSigningKey(jwtSecretKey))
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
