package com.example.skilltracker.dto.skillassignment.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateSkillAssignmentRequest(
        @NotNull
        Long userId,
        @NotNull
        Long skillId,
        @Min(value = 1, message = "Proficiency must be at least 1")
        @Max(value = 10, message = "Proficiency cannot exceed 10")
        int proficiency
) {}
