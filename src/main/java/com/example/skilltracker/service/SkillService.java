package com.example.skilltracker.service;

import com.example.skilltracker.entity.CategoryEntity;
import com.example.skilltracker.entity.exception.EntityNotFoundException;
import com.example.skilltracker.entity.SkillEntity;
import com.example.skilltracker.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final CategoryService categoryService;

    public List<SkillEntity> findAll() {
        return skillRepository.findAll();
    }

    public SkillEntity findById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SkillEntity.class, id));
    }

    public SkillEntity create(String name, int level, Long categoryId) {
        CategoryEntity category = categoryService.findById(categoryId);
        if (category == null) {
            throw new EntityNotFoundException(CategoryEntity.class, categoryId);
        }

        return skillRepository.save(new SkillEntity(name, level, category));
    }

    public void delete(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new EntityNotFoundException(SkillEntity.class, id);
        }
        skillRepository.deleteById(id);
    }
}
