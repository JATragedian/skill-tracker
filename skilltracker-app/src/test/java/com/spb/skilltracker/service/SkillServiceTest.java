package com.spb.skilltracker.service;

import com.spb.skilltracker.entity.CategoryEntity;
import com.spb.skilltracker.entity.SkillEntity;
import com.spb.skilltracker.entity.exception.EntityNotFoundException;
import com.spb.skilltracker.repository.SkillRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private SkillService skillService;

    @Test
    void create_should_save_skill_with_resolved_category() {

        // Given
        Long categoryId = 1L;
        String name = "Java";
        int level = 3;

        CategoryEntity category = new CategoryEntity("Backend");
        SkillEntity savedSkill = new SkillEntity(name, level, category);

        when(categoryService.findById(categoryId)).thenReturn(category);
        when(skillRepository.save(any(SkillEntity.class))).thenReturn(savedSkill);

        // When
        SkillEntity result = skillService.create(name, level, categoryId);

        // Then
        ArgumentCaptor<SkillEntity> skillCaptor = ArgumentCaptor.forClass(SkillEntity.class);
        verify(skillRepository).save(skillCaptor.capture());

        SkillEntity toSave = skillCaptor.getValue();

        assertAll(
                () -> assertEquals(name, toSave.getName(), "Skill name must be the same"),
                () -> assertEquals(level, toSave.getLevel(), "Skill level must be the same"),
                () -> assertSame(category, toSave.getCategory(), "Category must be the same"),
                () -> assertSame(savedSkill, result, "Result must be as expected")
        );
    }

    @Test
    void create_should_throw_when_category_not_found() {

        // Given
        Long categoryId = 99L;
        when(categoryService.findById(categoryId)).thenReturn(null);

        // When
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> skillService.create("Kotlin", 2, categoryId)
        );

        // Then
        assertAll(
                () -> assertEquals(categoryId, exception.getId(), "Category ID must be the same"),
                () -> verify(skillRepository, never()).save(any())
        );
    }
}
