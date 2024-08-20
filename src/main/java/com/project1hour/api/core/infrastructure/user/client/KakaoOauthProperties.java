package com.project1hour.api.core.infrastructure.user.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO: 외부 설정 검토 (url.profile -> profile-url)
 */
@ConfigurationProperties("oauth2.kakao.url")
public record KakaoOauthProperties(String profile) {
}
