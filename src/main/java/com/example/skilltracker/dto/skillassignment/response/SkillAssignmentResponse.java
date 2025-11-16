package com.example.skilltracker.dto.skillassignment.response;

public record SkillAssignmentResponse(
        Long id,
        Long userId,
        Long skillId,
        int proficiency
) {}
