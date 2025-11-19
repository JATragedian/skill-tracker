package com.spb.skilltracker.service;

import com.spb.skilltracker.entity.user.Role;
import com.spb.skilltracker.entity.user.UserEntity;
import com.spb.skilltracker.entity.exception.EntityNotFoundException;
import com.spb.skilltracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    @Cacheable(value = "userById", key = "#id")
    @Transactional(readOnly = true)
    public UserEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, id));
    }

    @CacheEvict(value = {"userById"}, allEntries = true)
    public UserEntity register(String name, String email, String password) {
        if (repository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists with email: " + email);
        }

        return repository.save(new UserEntity(name, email, passwordEncoder.encode(password), Role.USER));
    }

    @CacheEvict(value = {"userById"}, allEntries = true)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(UserEntity.class, id);
        }
        repository.deleteById(id);
    }
}
