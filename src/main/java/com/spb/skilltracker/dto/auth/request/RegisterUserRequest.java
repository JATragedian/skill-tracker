package com.spb.skilltracker.dto.auth.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequest(
        @NotBlank(message = "Name cannot be empty")
        String name,
        @NotBlank(message = "Email cannot be empty")
        String email,
        String password
) {}
