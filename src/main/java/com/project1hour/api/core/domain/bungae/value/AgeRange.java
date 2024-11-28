package com.project1hour.api.core.domain.bungae.value;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record AgeRange(
        @Column(nullable = false)
        int ageLowerLimit,

        @Column(nullable = false)
        int ageUpperLimit
) {

    private static final int MIN_AGE_VALUE = 18;
    private static final int MAX_AGE_VALUE = 44;

    public AgeRange {
        validateAgeLimits(ageLowerLimit, ageUpperLimit);
        validateAgeBounds(ageUpperLimit, ageLowerLimit);
    }

    private void validateAgeLimits(final int ageLowerLimit, final int ageUpperLimit) {
        if (ageLowerLimit < MIN_AGE_VALUE) {
            String message = String.format("번개 참여 나이는 %d세 이상이어야 합니다. 입력 값 = %d", MIN_AGE_VALUE, ageLowerLimit);
            throw new BadRequestException(message, ErrorCode.INVALID_BUNGAE_AGE_MIN);
        }

        if (ageUpperLimit > MAX_AGE_VALUE) {
            String message = String.format("번개 참여 나이는 %d세 이하이어야 합니다. 입력 값 = %d", MAX_AGE_VALUE, ageUpperLimit);
            throw new BadRequestException(message, ErrorCode.INVALID_BUNGAE_AGE_MAX);
        }
    }

    private void validateAgeBounds(final int ageUpperLimit, final int ageLowerLimit) {
        if (ageUpperLimit < ageLowerLimit) {
            String message = String.format("번개 참여 최소 나이는 최대 나이보다 클 수 없습니다. 최소 값 = %d, 최대 값 = %d",
                    ageLowerLimit, ageUpperLimit);
            throw new BadRequestException(message, ErrorCode.INVALID_BUNGAE_AGE_BOUNDS);
        }
    }
}
