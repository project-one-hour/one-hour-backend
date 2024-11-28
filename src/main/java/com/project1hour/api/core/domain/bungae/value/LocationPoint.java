package com.project1hour.api.core.domain.bungae.value;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;

public record LocationPoint(Double latitude, Double longitude) {

    // 위도 범위
    public static final double LATITUDE_MIN = -90.0;
    public static final double LATITUDE_MAX = 90.0;

    // 경도 범위
    public static final double LONGITUDE_MIN = -180.0;
    public static final double LONGITUDE_MAX = 180.0;

    public LocationPoint {
        validatePoint(latitude, longitude);
    }

    private void validatePoint(final Double latitude, final Double longitude) {
        if (latitude < LATITUDE_MIN || latitude > LATITUDE_MAX) {
            String message = String.format("유효하지 않은 위도 범위 입니다. 입력 값 = %f", latitude);
            throw new BadRequestException(message, ErrorCode.INVALID_LATITUDE);
        }

        if (longitude < LONGITUDE_MIN || longitude > LONGITUDE_MAX) {
            String message = String.format("유효하지 않은 경도 범위 입니다. 입력 값 = %f", longitude);
            throw new BadRequestException(message, ErrorCode.INVALID_LONGITUDE);
        }
    }
}
