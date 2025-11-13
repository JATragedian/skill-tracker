package com.example.skilltracker.model;

import jakarta.validation.constraints.NotBlank;

public record Skill(
        Long id,
        String name,
        int level
) {}
