package com.project1hour.api.core.domain.bungae.value;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;

public record Capacity(Integer count) {

    public static final int MAX_CAPACITY_PER_BUNGAE = 6;
    public static final int MIN_CAPACITY_PER_BUNGAE = 1;

    public Capacity {
        validateCapacityLimit(count);
    }

    private void validateCapacityLimit(final Integer count) {
        if (count < MIN_CAPACITY_PER_BUNGAE) {
            String message = String.format("번개 모집 인원 수가 1명 미만일 수 없습니다. 모집 인원 수 = %d", count);
            throw new BadRequestException(message, ErrorCode.INVALID_CAPACITY_COUNT_MIN);
        }

        if (count > MAX_CAPACITY_PER_BUNGAE) {
            String message = String.format("번개 모집 인원 수가 6명을 초과할 수 없습니다. 모집 인원 수 = %d", count);
            throw new BadRequestException(message, ErrorCode.INVALID_CAPACITY_COUNT_MAX);
        }
    }
}
