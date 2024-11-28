package com.project1hour.api.core.domain.user.value;

import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.NotFoundException;
import java.util.Arrays;

public enum Mbti {
    ISTJ, ISTP, ISFJ, ISFP,
    INTJ, INTP, INFJ, INFP,
    ESTJ, ESTP, ESFJ, ESFP,
    ENTJ, ENTP, ENFJ, ENFP;

    public static Mbti find(final String value) {
        String upperCaseValue = value.toUpperCase();

        return Arrays.stream(values())
                .filter(mbti -> upperCaseValue.equals(mbti.name()))
                .findFirst()
                .orElseThrow(() -> {
                    String message = String.format("%s와 일치하는 MBTI를 찾을 수 없습니다.", upperCaseValue);
                    return new NotFoundException(message, ErrorCode.INVALID_GENDER_VALUE);
                });
    }
}
