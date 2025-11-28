package com.spb.skilltracker.service;

import com.spb.skilltracker.entity.CategoryEntity;
import com.spb.skilltracker.entity.exception.EntityNotFoundException;
import com.spb.skilltracker.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void findAll_should_return_all_categories() {

        // Given
        List<CategoryEntity> categories = List.of(new CategoryEntity("Backend"), new CategoryEntity("Frontend"));
        when(categoryRepository.findAll()).thenReturn(categories);

        // When
        List<CategoryEntity> result = categoryService.findAll();

        // Then
        assertEquals(categories, result, "Should return all categories");
    }

    @Test
    void findById_should_return_category_when_exists() {

        // Given
        Long id = 5L;
        CategoryEntity category = new CategoryEntity("Data");
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        // When
        CategoryEntity result = categoryService.findById(id);

        // Then
        assertSame(category, result, "Should return the found category");
    }

    @Test
    void findById_should_throw_when_missing() {

        // Given
        Long id = 11L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> categoryService.findById(id));

        // Then
        assertEquals(id, exception.getId(), "Exception should contain requested id");
    }

    @Test
    void create_should_save_new_category() {

        // Given
        String name = "Mobile";
        CategoryEntity saved = new CategoryEntity(name);
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(saved);

        // When
        CategoryEntity result = categoryService.create(name);

        // Then
        verify(categoryRepository).save(any(CategoryEntity.class));
        assertSame(saved, result, "Saved category should be returned");
    }

    @Test
    void delete_should_remove_existing_category() {

        // Given
        Long id = 3L;
        when(categoryRepository.existsById(id)).thenReturn(true);

        // When
        categoryService.delete(id);

        // Then
        verify(categoryRepository).deleteById(id);
    }

    @Test
    void delete_should_throw_when_category_missing() {

        // Given
        Long id = 8L;
        when(categoryRepository.existsById(id)).thenReturn(false);

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> categoryService.delete(id));

        // Then
        assertEquals(id, exception.getId(), "Exception should contain id for deletion");
    }
}
