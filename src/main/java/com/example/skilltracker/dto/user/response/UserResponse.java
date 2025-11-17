package com.example.skilltracker.dto.user.response;

public record UserResponse(
        Long id,
        String name,
        String email,
        String role
) {}
