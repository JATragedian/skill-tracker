package com.spb.skilltracker.dto.auth.response;

public record UserResponse(
        Long id,
        String name,
        String email,
        String role
) {}
