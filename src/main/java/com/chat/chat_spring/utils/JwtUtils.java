package com.chat.chat_spring.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Requirement 2, handle and process HTTP requests.
 * JWT Utils service
 */
@Service
public class JwtUtils {
    private static final String SECRET_KEY = "secret";

    /**
     * Requirement 2, handle and process HTTP requests.
     * Generates new JWT token.
     * @param userDetails existing user userDetails object
     * @return JWT string
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Requirement 2, handle and process HTTP requests.
     * Validates JWT token
     * @param token JWT string
     * @param userDetails UserDetails object
     * @return boolean
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        String userName = extractUsername(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Requirement 2, handle and process HTTP requests.
     * Creates new token
     * @return JWT token string
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date(System.currentTimeMillis());
        Date util = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now).setExpiration(util)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Helper method
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Helper method
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Helper method
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Helper method
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    /**
     * Helper method
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
