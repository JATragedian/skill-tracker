package com.example.skilltracker.dto.auth.response;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
