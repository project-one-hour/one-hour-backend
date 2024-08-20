package com.project1hour.api.core.domain.user.value;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.util.Arrays;

public enum AuthProvider {
    KAKAO, APPLE;

    public static AuthProvider find(final String providerName) {
        return Arrays.stream(values())
                .filter(authProvider -> authProvider.name().equalsIgnoreCase(providerName))
                .findFirst()
                .orElseThrow(() -> {
                    String message = String.format("지원하지 않는 소셜 인증입니다 . input = %s", providerName);
                    return new BadRequestException(message, ErrorCode.OAUTH_PROVIDER_NOT_FOUND);
                });
    }
}
