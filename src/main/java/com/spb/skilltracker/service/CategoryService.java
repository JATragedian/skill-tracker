package com.spb.skilltracker.service;

import com.spb.skilltracker.entity.CategoryEntity;
import com.spb.skilltracker.entity.exception.EntityNotFoundException;
import com.spb.skilltracker.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository repository;

    @Cacheable("categories")
    @Transactional(readOnly = true)
    public List<CategoryEntity> findAll() {
        return repository.findAll();
    }

    @Cacheable(value = "categoryById", key = "#id")
    @Transactional(readOnly = true)
    public CategoryEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CategoryEntity.class, id));
    }

    @CacheEvict(value = "categories", allEntries = true)
    public CategoryEntity create(String name) {
        CategoryEntity category = new CategoryEntity(name);
        return repository.save(category);
    }

    @CacheEvict(value = {"categories", "categoryById"}, allEntries = true)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(CategoryEntity.class, id);
        }
        repository.deleteById(id);
    }
}
