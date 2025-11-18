package com.example.skilltracker.service;

import com.example.skilltracker.entity.user.Role;
import com.example.skilltracker.entity.user.UserEntity;
import com.example.skilltracker.entity.exception.EntityNotFoundException;
import com.example.skilltracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity register(String name, String email, String password) {
        if (repository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists with email: " + email);
        }

        return repository.save(new UserEntity(name, email, passwordEncoder.encode(password), Role.USER));
    }

    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    public UserEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, id));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(UserEntity.class, id);
        }
        repository.deleteById(id);
    }
}
