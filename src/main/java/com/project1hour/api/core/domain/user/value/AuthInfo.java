package com.project1hour.api.core.domain.user.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * 소프트웨어 용어를 최대한 피하기 위해 인증 정보는 AuthInfo 레코드를 사용하여 한 곳에 모아둡니다. <br/>
 * TODO: 카카오 > 회원번호, 애플 > 이름? 이메일? 고유 식별자를 알아내야함 <br/>
 * TODO: 애플 고유 식별자가 정수타입일 경우 String -> Long
 */
@Embeddable
public record AuthInfo(
        @Column(nullable = false, unique = true)
        String socialProfileId,

        @Column(nullable = false)
        String accessToken,

        @Column(nullable = false)
        String refreshToken
) {
}
