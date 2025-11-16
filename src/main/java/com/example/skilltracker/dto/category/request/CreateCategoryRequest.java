package com.example.skilltracker.dto.category.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank(message = "Category name cannot be empty")
        String name
) {}
