package com.project1hour.api.auth.application.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
