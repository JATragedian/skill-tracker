package com.spb.skilltracker.dto.skill.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateSkillRequest(
        @NotBlank(message = "Skill name cannot be empty")
        String name,

        @Min(value = 1, message = "Skill level must be at least 1")
        @Max(value = 10, message = "Skill level cannot exceed 10")
        int level,

        @NotBlank(message = "Category ID cannot be empty")
        Long categoryId
) {}
