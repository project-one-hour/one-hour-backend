package com.project1hour.api.core.infrastructure.user.client;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.project1hour.api.core.application.user.api.OauthClient2;
import com.project1hour.api.core.application.user.api.SocialProfileId;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(KakaoOauthProperties.class)
public class KakaoOauthClient2 implements OauthClient2 {

    public static final String KAKAO_OAUTH_PROVIDER = "KAKAO";

    private final KakaoOauthProperties properties;
    private final RestClient restClient = RestClient.create();

    @Override
    public boolean isSupport(final String provider) {
        return provider.equalsIgnoreCase(KAKAO_OAUTH_PROVIDER);
    }

    @Override
    public SocialProfileId findSocialProfileIdByAccessToken(final String accessToken) {
        return restClient.get()
                .uri(properties.profile())
                .header(AUTHORIZATION, convertToBearerToken(accessToken))
                .retrieve()
                .body(KakaoSocialProfileId.class);
    }

    private String convertToBearerToken(final String accessToken) {
        return "Bearer " + accessToken;
    }
}
