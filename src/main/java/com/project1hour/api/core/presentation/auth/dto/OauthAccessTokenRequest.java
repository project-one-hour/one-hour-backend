package com.project1hour.api.core.presentation.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record OauthAccessTokenRequest(
        @NotBlank(message = "token은 null이거나 빈 값일 수 없습니다.")
        String token
) {
}
