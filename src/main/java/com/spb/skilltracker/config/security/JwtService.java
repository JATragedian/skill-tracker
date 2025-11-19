package com.spb.skilltracker.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final SecretKey key;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    public JwtService(@Value("${app.jwt.secret}") String secret) {
        byte[] bytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(expirationMs)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return resolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        Instant exp = extractClaim(token, Claims::getExpiration).toInstant();
        return exp.isBefore(Instant.now());
    }

    public boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }
}
