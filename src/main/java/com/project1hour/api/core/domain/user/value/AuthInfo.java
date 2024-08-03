package com.project1hour.api.core.domain.user.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;

/**
 * 소프트웨어 용어를 최대한 피하기 위해 인증 정보는 AuthInfo 레코드를 사용하여 한 곳에 모아둡니다. <br/>
 * TODO: 카카오 > 회원번호, 애플 > 이름? 이메일? 고유 식별자를 알아내야함
 */
@Embeddable
public record AuthInfo(
        @Column(nullable = false, unique = true)
        String identifierKey,

        @Column(nullable = false)
        String accessToken,

        @Column(nullable = false)
        String refreshToken) {

    @Builder
    public AuthInfo {
    }
}
