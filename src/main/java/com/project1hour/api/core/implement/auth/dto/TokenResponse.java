package com.project1hour.api.core.implement.auth.dto;

public record TokenResponse(
        String accessToken,
        boolean isNew
) {
}
