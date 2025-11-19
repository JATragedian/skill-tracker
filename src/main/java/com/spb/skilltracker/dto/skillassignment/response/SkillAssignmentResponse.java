package com.spb.skilltracker.dto.skillassignment.response;

public record SkillAssignmentResponse(
        Long id,
        Long userId,
        Long skillId,
        int proficiency
) {}
