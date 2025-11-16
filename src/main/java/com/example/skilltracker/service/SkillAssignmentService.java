package com.example.skilltracker.service;

import com.example.skilltracker.entity.SkillAssignmentEntity;
import com.example.skilltracker.entity.SkillEntity;
import com.example.skilltracker.entity.UserEntity;
import com.example.skilltracker.entity.exception.DuplicateEntityException;
import com.example.skilltracker.entity.exception.EntityNotFoundException;
import com.example.skilltracker.repository.SkillAssignmentRepository;
import com.example.skilltracker.repository.SkillRepository;
import com.example.skilltracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillAssignmentService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final SkillAssignmentRepository skillAssignmentRepository;
    private final ErrorLogService errorLogService;

    public SkillAssignmentService(
            UserRepository userRepository,
            SkillRepository skillRepository,
            SkillAssignmentRepository skillAssignmentRepository,
            ErrorLogService errorLogService
    ) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.skillAssignmentRepository = skillAssignmentRepository;
        this.errorLogService = errorLogService;
    }

    @Transactional
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
}
