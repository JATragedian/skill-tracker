package com.example.skilltracker.dto.skillassignment.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AssignMultipleSkillsRequest(
        @NotNull
        Long userId,
        List<Long> skillIds,
        @Min(value = 1, message = "Proficiency must be at least 1")
        @Max(value = 100, message = "Proficiency cannot exceed 100")
        int proficiency
) {}
