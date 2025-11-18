package com.example.skilltracker.service.auth;

import com.example.skilltracker.entity.auth.RefreshTokenEntity;
import com.example.skilltracker.entity.user.UserEntity;
import com.example.skilltracker.repository.auth.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    @Value("${app.jwt.refresh-expiration-sec}")
    private long expirationSec;

    public RefreshTokenEntity create(UserEntity user) {
        var token = new RefreshTokenEntity(
                user,
                UUID.randomUUID().toString(),
                Instant.now().plusSeconds(expirationSec)
        );
        return repository.save(token);
    }

    public RefreshTokenEntity validate(String token) {
        var refresh = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refresh.getExpiresAt().isBefore(Instant.now())) {
            delete(refresh);
            throw new RuntimeException("Refresh token expired");
        }

        return refresh;
    }

    public void delete(RefreshTokenEntity token) {
        repository.delete(token);
    }
}
