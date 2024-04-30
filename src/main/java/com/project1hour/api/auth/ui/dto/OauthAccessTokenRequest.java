package com.project1hour.api.auth.ui.dto;

import jakarta.validation.constraints.NotBlank;

public record OauthAccessTokenRequest(
        @NotBlank(message = "provider는 null이거나 빈 값일 수 없습니다.")
        String provider,
        @NotBlank(message = "token은 null이거나 빈 값일 수 없습니다.")
        String token
) {
}
