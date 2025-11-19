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
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SkillAssignmentService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final SkillAssignmentRepository skillAssignmentRepository;
    private final ErrorLogService errorLogService;

    @Cacheable("skillAssignments")
    @Transactional(readOnly = true)
    public List<SkillAssignmentEntity> findAll() {
        return skillAssignmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<SkillAssignmentEntity> findByUserId(Long id) {
        return skillAssignmentRepository.findByUserId(id);
    }

    @Cacheable(value = "assignmentById", key = "#id")
    @Transactional(readOnly = true)
    public SkillAssignmentEntity findById(Long id) {
        return skillAssignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SkillAssignmentEntity.class, id));
    }

    @Transactional
    @CacheEvict(value = {"skillAssignments", "assignmentById"}, allEntries = true)
    public SkillAssignmentEntity assignSkillToUser(Long userId, Long skillId, int proficiency) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, userId));
        SkillEntity skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException(SkillEntity.class, skillId));

        if (skillAssignmentRepository.existsByUserAndSkill(user, skill)) {
            throw new DuplicateEntityException(SkillAssignmentEntity.class);
        }

        SkillAssignmentEntity assignment =
                new SkillAssignmentEntity(user, skill, proficiency);

        return skillAssignmentRepository.save(assignment);
    }

    @Transactional
    @CacheEvict(value = {"skillAssignments", "assignmentById"}, allEntries = true)
    public List<SkillAssignmentEntity> assignMultiple(Long userId, List<Long> skillIds, int proficiency) {
        List<SkillAssignmentEntity> skillAssignmentEntityList = new ArrayList<>();
        for (Long skillId : skillIds) {
            try {
                SkillAssignmentEntity skillAssignmentEntity = assignSkillToUser(userId, skillId, proficiency);
                skillAssignmentEntityList.add(skillAssignmentEntity);
            } catch (Exception ex) {
                errorLogService.logFailure(userId, skillId, ex.getMessage());
            }
        }

        return skillAssignmentEntityList;
    }

    @CacheEvict(value = {"skillAssignments", "assignmentById"}, allEntries = true)
    public SkillAssignmentEntity update(Long id, Long userId, Long skillId, int proficiency) {
        var assignment = skillAssignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SkillAssignmentEntity.class, id));

        var skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException(SkillEntity.class, skillId));

        assignment.setSkill(skill);
        assignment.setProficiency(proficiency);

        return skillAssignmentRepository.save(assignment);
    }

    @CacheEvict(value = {"skillAssignments", "assignmentById"}, allEntries = true)
    public void delete(Long id) {
        if (!skillAssignmentRepository.existsById(id)) {
            throw new EntityNotFoundException(SkillAssignmentEntity.class, id);
        }
        skillAssignmentRepository.deleteById(id);
    }
}
