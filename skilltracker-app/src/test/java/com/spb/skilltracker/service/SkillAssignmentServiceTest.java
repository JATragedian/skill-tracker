package com.spb.skilltracker.service;

import com.spb.skilltracker.entity.SkillAssignmentEntity;
import com.spb.skilltracker.entity.SkillEntity;
import com.spb.skilltracker.entity.exception.DuplicateEntityException;
import com.spb.skilltracker.entity.exception.EntityNotFoundException;
import com.spb.skilltracker.entity.user.UserEntity;
import com.spb.skilltracker.repository.SkillAssignmentRepository;
import com.spb.skilltracker.repository.SkillRepository;
import com.spb.skilltracker.repository.UserRepository;
import com.spb.skilltracker.service.log.ErrorLogService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SkillAssignmentServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private SkillAssignmentRepository skillAssignmentRepository;

    @Mock
    private ErrorLogService errorLogService;

    @InjectMocks
    private SkillAssignmentService skillAssignmentService;

    @Test
    void findAll_should_return_all_assignments() {

        // Given
        List<SkillAssignmentEntity> assignments = List.of(new SkillAssignmentEntity(), new SkillAssignmentEntity());
        when(skillAssignmentRepository.findAll()).thenReturn(assignments);

        // When
        List<SkillAssignmentEntity> result = skillAssignmentService.findAll();

        // Then
        assertSame(assignments, result, "All assignments should be returned");
    }

    @Test
    void findByUserId_should_return_assignments_for_user() {

        // Given
        Long userId = 10L;
        List<SkillAssignmentEntity> assignments = List.of(new SkillAssignmentEntity());
        when(skillAssignmentRepository.findByUserId(userId)).thenReturn(assignments);

        // When
        List<SkillAssignmentEntity> result = skillAssignmentService.findByUserId(userId);

        // Then
        assertSame(assignments, result, "Assignments for user should be returned");
    }

    @Test
    void findById_should_throw_when_assignment_missing() {

        // Given
        Long id = 13L;
        when(skillAssignmentRepository.findById(id)).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> skillAssignmentService.findById(id));

        // Then
        assertEquals(id, exception.getId(), "Exception should contain missing id");
    }

    @Test
    void assignSkillToUser_should_create_new_assignment() {

        // Given
        Long userId = 2L;
        Long skillId = 5L;
        int proficiency = 7;
        UserEntity user = new UserEntity();
        SkillEntity skill = new SkillEntity();
        SkillAssignmentEntity saved = new SkillAssignmentEntity(user, skill, proficiency);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(skillRepository.findById(skillId)).thenReturn(Optional.of(skill));
        when(skillAssignmentRepository.existsByUserAndSkill(user, skill)).thenReturn(false);
        when(skillAssignmentRepository.save(any(SkillAssignmentEntity.class))).thenReturn(saved);

        // When
        SkillAssignmentEntity result = skillAssignmentService.assignSkillToUser(userId, skillId, proficiency);

        // Then
        ArgumentCaptor<SkillAssignmentEntity> captor = ArgumentCaptor.forClass(SkillAssignmentEntity.class);
        verify(skillAssignmentRepository).save(captor.capture());

        SkillAssignmentEntity toSave = captor.getValue();
        assertAll(
                () -> assertSame(user, toSave.getUser(), "User should be attached"),
                () -> assertSame(skill, toSave.getSkill(), "Skill should be attached"),
                () -> assertEquals(proficiency, toSave.getProficiency(), "Proficiency should be set"),
                () -> assertSame(saved, result, "Saved assignment should be returned")
        );
    }

    @Test
    void assignSkillToUser_should_throw_when_user_missing() {

        // Given
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> skillAssignmentService.assignSkillToUser(userId, 2L, 5));

        // Then
        assertEquals(userId, exception.getId(), "Exception should include user id");
    }

    @Test
    void assignSkillToUser_should_throw_when_skill_missing() {

        // Given
        Long skillId = 7L;
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new UserEntity()));
        when(skillRepository.findById(skillId)).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> skillAssignmentService.assignSkillToUser(1L, skillId, 5));

        // Then
        assertEquals(skillId, exception.getId(), "Exception should include skill id");
    }

    @Test
    void assignSkillToUser_should_throw_when_assignment_exists() {

        // Given
        UserEntity user = new UserEntity();
        SkillEntity skill = new SkillEntity();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(skillRepository.findById(anyLong())).thenReturn(Optional.of(skill));
        when(skillAssignmentRepository.existsByUserAndSkill(user, skill)).thenReturn(true);

        // When
        assertThrows(DuplicateEntityException.class, () -> skillAssignmentService.assignSkillToUser(1L, 2L, 3));
    }

    @Test
    void assignMultiple_should_collect_successes_and_log_failures() {

        // Given
        Long userId = 5L;
        UserEntity user = new UserEntity();
        SkillEntity firstSkill = new SkillEntity();
        SkillEntity secondSkill = new SkillEntity();
        SkillAssignmentEntity assignment = new SkillAssignmentEntity(user, firstSkill, 6);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(skillRepository.findById(101L)).thenReturn(Optional.of(firstSkill));
        when(skillRepository.findById(202L)).thenReturn(Optional.of(secondSkill));
        when(skillAssignmentRepository.existsByUserAndSkill(user, firstSkill)).thenReturn(false);
        when(skillAssignmentRepository.existsByUserAndSkill(user, secondSkill)).thenReturn(false);
        when(skillAssignmentRepository.save(any(SkillAssignmentEntity.class)))
                .thenReturn(assignment)
                .thenThrow(new DuplicateEntityException(SkillAssignmentEntity.class));

        // When
        List<SkillAssignmentEntity> result = skillAssignmentService.assignMultiple(userId, List.of(101L, 202L), 6);

        // Then
        assertEquals(1, result.size(), "Only successful assignment should be collected");
        verify(errorLogService).logFailure(eq(userId), eq(202L), anyString());
    }

    @Test
    void update_should_change_skill_and_proficiency() {

        // Given
        Long assignmentId = 9L;
        Long newSkillId = 3L;
        SkillEntity currentSkill = new SkillEntity();
        SkillEntity newSkill = new SkillEntity();
        SkillAssignmentEntity assignment = new SkillAssignmentEntity(new UserEntity(), currentSkill, 1);

        when(skillAssignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(skillRepository.findById(newSkillId)).thenReturn(Optional.of(newSkill));
        when(skillAssignmentRepository.save(assignment)).thenReturn(assignment);

        // When
        SkillAssignmentEntity result = skillAssignmentService.update(assignmentId, 1L, newSkillId, 10);

        // Then
        assertAll(
                () -> assertSame(newSkill, assignment.getSkill(), "Skill should be updated"),
                () -> assertEquals(10, assignment.getProficiency(), "Proficiency should be updated"),
                () -> assertSame(assignment, result, "Updated assignment should be returned")
        );
    }

    @Test
    void update_should_throw_when_assignment_missing() {

        // Given
        Long id = 4L;
        when(skillAssignmentRepository.findById(id)).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> skillAssignmentService.update(id, 1L, 2L, 3));

        // Then
        assertEquals(id, exception.getId(), "Exception should include assignment id");
    }

    @Test
    void delete_should_throw_when_assignment_missing() {

        // Given
        Long id = 16L;
        when(skillAssignmentRepository.existsById(id)).thenReturn(false);

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> skillAssignmentService.delete(id));

        // Then
        assertEquals(id, exception.getId(), "Exception should include id");
    }
}
