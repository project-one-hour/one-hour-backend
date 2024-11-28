package com.project1hour.api.core.domain.user.value;

import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.NotFoundException;
import java.util.Arrays;

public enum ProfileImageType {
    PRIMARY, SECONDARY;

    public static ProfileImageType find(final String value) {
        String upperCaseValue = value.toUpperCase();

        return Arrays.stream(values())
                .filter(profileImageType -> upperCaseValue.equals(profileImageType.name()))
                .findFirst()
                .orElseThrow(() -> {
                    String message = String.format("일치하는 프로필 이미지 타입을 찾을 수 없습니다. 입력 값 = %s", upperCaseValue);
                    return new NotFoundException(message, ErrorCode.INVALID_GENDER_VALUE);
                });
    }
}
