package com.project1hour.api.core.domain.bungae.value;

import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.NotFoundException;
import java.util.Arrays;

public enum GenderType {
    NONE, FEMALE, MALE;

    public static GenderType find(final String value) {
        String upperCaseValue = value.toUpperCase();

        return Arrays.stream(values())
                .filter(genderType -> upperCaseValue.equals(genderType.name()))
                .findFirst()
                .orElseThrow(() -> {
                    String message = String.format("일치하는 번개 성별 타입을 찾을 수 없습니다. 입력 성별 타입 = %s", upperCaseValue);
                    return new NotFoundException(message, ErrorCode.INVALID_BUNGAE_GENDER_TYPE_VALUE);
                });
    }
}
