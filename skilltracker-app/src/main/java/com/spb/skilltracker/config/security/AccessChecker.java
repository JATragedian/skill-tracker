package com.spb.skilltracker.config.security;

import com.spb.skilltracker.repository.SkillAssignmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component("ac")
@AllArgsConstructor
public class AccessChecker {

    private final SkillAssignmentRepository skillAssignmentRepository;

    public boolean canAccessAssignment(Long id, String email) {
        return skillAssignmentRepository.findById(id)
                .map(a -> a.getUser().getEmail().equals(email))
                .orElse(false);
    }
}
