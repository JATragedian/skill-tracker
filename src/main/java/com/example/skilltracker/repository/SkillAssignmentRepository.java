package com.example.skilltracker.repository;

import com.example.skilltracker.entity.SkillAssignmentEntity;
import com.example.skilltracker.entity.SkillEntity;
import com.example.skilltracker.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillAssignmentRepository extends JpaRepository<SkillAssignmentEntity, Long> {

    boolean existsByUserAndSkill(UserEntity user, SkillEntity skill);
}
