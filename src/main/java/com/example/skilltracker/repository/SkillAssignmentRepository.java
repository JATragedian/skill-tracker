package com.example.skilltracker.repository;

import com.example.skilltracker.entity.SkillAssignmentEntity;
import com.example.skilltracker.entity.SkillEntity;
import com.example.skilltracker.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillAssignmentRepository extends JpaRepository<SkillAssignmentEntity, Long> {

    boolean existsByUserAndSkill(UserEntity user, SkillEntity skill);

    List<SkillAssignmentEntity> findByUserId(Long id);
}
