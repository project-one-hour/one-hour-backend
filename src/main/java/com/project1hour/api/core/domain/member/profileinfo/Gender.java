package com.project1hour.api.core.domain.member.profileinfo;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.util.Arrays;

public enum Gender {
    MALE, FEMALE;

    public static Gender find(final String value) {
        String upperCaseValue = value.toUpperCase();

        return Arrays.stream(values())
                .filter(gender -> upperCaseValue.equals(gender.name()))
                .findFirst()
                .orElseThrow(() -> {
                    String message = String.format("일치하는 성별을 찾을 수 없습니다. 입력성별 = %s", upperCaseValue);
                    return new BadRequestException(message, ErrorCode.INVALID_GENDER_VALUE);
                });
    }
}
