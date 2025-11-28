package com.spb.skilltracker.service.auth;

import com.spb.skilltracker.entity.auth.RefreshTokenEntity;
import com.spb.skilltracker.entity.user.UserEntity;
import com.spb.skilltracker.repository.auth.RefreshTokenRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Test
    void create_should_generate_token_with_expiration() {

        // Given
        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(refreshTokenService, "expirationSec", 120L);
        RefreshTokenEntity saved = new RefreshTokenEntity(user, "token", Instant.now().plusSeconds(120));
        when(refreshTokenRepository.save(any(RefreshTokenEntity.class))).thenReturn(saved);

        // When
        RefreshTokenEntity result = refreshTokenService.create(user);

        // Then
        verify(refreshTokenRepository).save(any(RefreshTokenEntity.class));
        assertSame(saved, result, "Saved token should be returned");
    }

    @Test
    void validate_should_return_token_when_valid() {

        // Given
        String token = "refresh";
        RefreshTokenEntity refresh = new RefreshTokenEntity(new UserEntity(), token, Instant.now().plusSeconds(30));
        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.of(refresh));

        // When
        RefreshTokenEntity result = refreshTokenService.validate(token);

        // Then
        assertSame(refresh, result, "Valid token should be returned");
    }

    @Test
    void validate_should_throw_and_delete_when_expired() {

        // Given
        String token = "expired";
        RefreshTokenEntity refresh = new RefreshTokenEntity(new UserEntity(), token, Instant.now().minusSeconds(5));
        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.of(refresh));

        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> refreshTokenService.validate(token));

        // Then
        assertEquals("Refresh token expired", exception.getMessage(), "Should report expiration");
        verify(refreshTokenRepository).delete(refresh);
    }

    @Test
    void validate_should_throw_when_missing() {

        // Given
        String token = "missing";
        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.empty());

        // When
        assertThrows(RuntimeException.class, () -> refreshTokenService.validate(token));
    }

    @Test
    void delete_should_remove_token() {

        // Given
        RefreshTokenEntity refresh = new RefreshTokenEntity();

        // When
        refreshTokenService.delete(refresh);

        // Then
        verify(refreshTokenRepository).delete(refresh);
    }
}
