package com.example.skilltracker.config.security;

import com.example.skilltracker.repository.SkillAssignmentRepository;
import org.springframework.stereotype.Component;

@Component("ac")
public class AccessChecker {

    private final SkillAssignmentRepository skillAssignmentRepository;

    public AccessChecker(SkillAssignmentRepository skillAssignmentRepository) {
        this.skillAssignmentRepository = skillAssignmentRepository;
    }

    public boolean canAccessAssignment(Long id, String email) {
        return skillAssignmentRepository.findById(id)
                .map(a -> a.getUser().getEmail().equals(email))
                .orElse(false);
    }
}
