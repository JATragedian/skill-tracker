package com.spb.skilltracker.service;

import com.spb.skilltracker.entity.CategoryEntity;
import com.spb.skilltracker.entity.exception.EntityNotFoundException;
import com.spb.skilltracker.entity.SkillEntity;
import com.spb.skilltracker.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final CategoryService categoryService;

    @Cacheable("skills")
    @Transactional(readOnly = true)
    public List<SkillEntity> findAll() {
        return skillRepository.findAll();
    }

    @Cacheable(value = "skillById", key = "#id")
    @Transactional(readOnly = true)
    public SkillEntity findById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SkillEntity.class, id));
    }

    @CacheEvict(value = {"skills", "skillById"}, allEntries = true)
    public SkillEntity create(String name, int level, Long categoryId) {
        CategoryEntity category = categoryService.findById(categoryId);
        if (category == null) {
            throw new EntityNotFoundException(CategoryEntity.class, categoryId);
        }

        return skillRepository.save(new SkillEntity(name, level, category));
    }

    @CacheEvict(value = {"skills", "skillById"}, allEntries = true)
    public void delete(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new EntityNotFoundException(SkillEntity.class, id);
        }
        skillRepository.deleteById(id);
    }
}
