package com.project1hour.api.core.application.bungae;

import com.project1hour.api.core.domain.bungae.entity.Bungae;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class LocationConfigurationUseCase {

    // 위도 범위
    public static final double LATITUDE_MIN = -90.0;
    public static final double LATITUDE_MAX = 90.0;

    // 경도 범위
    public static final double LONGITUDE_MIN = -180.0;
    public static final double LONGITUDE_MAX = 180.0;


    // TODO
    // 만약 도메인 엔티티와 jpa 엔티티가 분리가 된다면 위도, 경도 유효성 검사는 도메인으로
    protected void configureLocation(final Bungae bungae, final double latitude, final double longitude) {
        if (bungae.isChatType()) {
            throw new BadRequestException("채팅 번개는 장소를 지정할 수 없습니다.", ErrorCode.BUNGAE_CHAT_CAN_NOT_HAVE_LOCATION);
        }

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
