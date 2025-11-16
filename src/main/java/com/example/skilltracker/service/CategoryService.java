package com.example.skilltracker.service;

import com.example.skilltracker.entity.CategoryEntity;
import com.example.skilltracker.entity.exception.EntityNotFoundException;
import com.example.skilltracker.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<CategoryEntity> findAll() {
        return repository.findAll();
    }

    public CategoryEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CategoryEntity.class, id));
    }

    public CategoryEntity create(String name) {
        CategoryEntity category = new CategoryEntity(name);
        return repository.save(category);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(CategoryEntity.class, id);
        }
        repository.deleteById(id);
    }
}
