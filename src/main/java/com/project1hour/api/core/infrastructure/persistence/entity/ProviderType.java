package com.project1hour.api.core.infrastructure.persistence.entity;

import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.InfraStructureException;
import java.util.Arrays;

public enum ProviderType {
    KAKAO, APPLE;

    public static ProviderType find(final String providerName) {
        return Arrays.stream(values())
                .filter(providerType -> providerType.name().equalsIgnoreCase(providerName))
                .findFirst()
                .orElseThrow(() -> {
                    String message = String.format("지원하지 않는 소셜 인증입니다 . input = %s", providerName);
                    return new InfraStructureException(message, ErrorCode.OAUTH_PROVIDER_NOT_FOUND);
                });
    }
}
