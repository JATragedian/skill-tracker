package com.spb.skilltracker.service;

import com.spb.skilltracker.entity.exception.EntityNotFoundException;
import com.spb.skilltracker.entity.user.Role;
import com.spb.skilltracker.entity.user.UserEntity;
import com.spb.skilltracker.repository.UserRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void findAll_should_return_all_users() {

        // Given
        List<UserEntity> users = List.of(new UserEntity(), new UserEntity());
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<UserEntity> result = userService.findAll();

        // Then
        assertSame(users, result, "All users should be returned");
    }

    @Test
    void findById_should_return_user_when_present() {

        // Given
        Long id = 2L;
        UserEntity user = new UserEntity();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // When
        UserEntity result = userService.findById(id);

        // Then
        assertSame(user, result, "Expected to receive stored user");
    }

    @Test
    void findById_should_throw_when_user_missing() {

        // Given
        Long id = 9L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.findById(id));

        // Then
        assertEquals(id, exception.getId(), "Exception should hold missing id");
    }

    @Test
    void register_should_encode_password_and_save_user() {

        // Given
        String name = "Jane";
        String email = "jane@example.com";
        String password = "secret";
        String encoded = "ENCODED";
        UserEntity saved = new UserEntity(name, email, encoded, Role.USER);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn(encoded);
        when(userRepository.save(any(UserEntity.class))).thenReturn(saved);

        // When
        UserEntity result = userService.register(name, email, password);

        // Then
        verify(passwordEncoder).encode(password);
        verify(userRepository).save(any(UserEntity.class));
        assertSame(saved, result, "Saved user should be returned");
    }

    @Test
    void register_should_throw_when_email_exists() {

        // Given
        String email = "duplicate@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new UserEntity()));

        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.register("Test", email, "pwd"));

        // Then
        assertTrue(exception.getMessage().contains(email), "Exception should mention email");
        verify(userRepository, never()).save(any());
    }

    @Test
    void delete_should_remove_existing_user() {

        // Given
        Long id = 4L;
        when(userRepository.existsById(id)).thenReturn(true);

        // When
        userService.delete(id);

        // Then
        verify(userRepository).deleteById(id);
    }

    @Test
    void delete_should_throw_when_user_missing() {

        // Given
        Long id = 6L;
        when(userRepository.existsById(id)).thenReturn(false);

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.delete(id));

        // Then
        assertEquals(id, exception.getId(), "Exception should contain id of user");
    }
}
