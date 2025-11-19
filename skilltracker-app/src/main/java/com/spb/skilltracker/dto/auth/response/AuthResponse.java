package com.spb.skilltracker.dto.auth.response;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
