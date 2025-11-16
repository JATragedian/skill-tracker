package com.example.skilltracker.service;

import com.example.skilltracker.entity.exception.EntityNotFoundException;
import com.example.skilltracker.entity.SkillEntity;
import com.example.skilltracker.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository repository;

    public SkillService(SkillRepository repository) {
        this.repository = repository;
    }

    public List<SkillEntity> findAll() {
        return repository.findAll();
    }

    public SkillEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SkillEntity.class, id));
    }

    public SkillEntity create(String name, int level) {
        return repository.save(new SkillEntity(name, level));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(SkillEntity.class, id);
        }
        repository.deleteById(id);
    }
}
