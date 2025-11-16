package com.example.skilltracker.dto.user.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank(message = "Username cannot be empty")
        String username,
        @NotBlank(message = "Email cannot be empty")
        String email
) {}
