package com.example.skilltracker.service;

import com.example.skilltracker.entity.UserEntity;
import com.example.skilltracker.entity.exception.EntityNotFoundException;
import com.example.skilltracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    public UserEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, id));
    }

    public UserEntity create(String username, String email) {
        return repository.save(new UserEntity(username, email));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(UserEntity.class, id);
        }
        repository.deleteById(id);
    }
}
