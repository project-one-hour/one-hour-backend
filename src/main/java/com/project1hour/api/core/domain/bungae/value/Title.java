package com.project1hour.api.core.domain.bungae.value;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Title(
        @Column(name = "title", nullable = false, length = 40)
        String value
) {

    private static final int MAX_TITLE_LENGTH = 40;

    public Title {
        validateTitleLength(value);
    }

    private void validateTitleLength(final String value) {
        if (value.length() > MAX_TITLE_LENGTH) {
            String message = String.format("번개 제목은 최대 %d자까지 입력 가능합니다. 현재 길이 = %d", MAX_TITLE_LENGTH, value.length());
            throw new BadRequestException(message, ErrorCode.INVALID_BUNGAE_TITLE_LENGTH);
        }
    }
}
