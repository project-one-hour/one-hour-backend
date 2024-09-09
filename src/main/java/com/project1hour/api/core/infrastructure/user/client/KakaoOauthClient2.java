package com.project1hour.api.core.infrastructure.user.client;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.project1hour.api.core.application.user.api.OauthClient2;
import com.project1hour.api.core.application.user.api.data.SocialProfileId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class KakaoOauthClient2 implements OauthClient2 {

    private static final String BEARER_PREFIX = "Bearer ";
    public static final String KAKAO_OAUTH_PROVIDER = "KAKAO";

    private final String profileUrl;
    private final RestClient restClient = RestClient.create();

    public KakaoOauthClient2(@Value("${oauth2.kakao.url.profile}") final String profileUrl) {
        this.profileUrl = profileUrl;
    }

    @Override
    public boolean isSupport(final String provider) {
        return provider.equalsIgnoreCase(KAKAO_OAUTH_PROVIDER);
    }

    @Override
    public SocialProfileId findSocialProfileIdByAccessToken(final String accessToken) {
        return restClient.get()
                .uri(profileUrl)
                .header(AUTHORIZATION, convertToBearerToken(accessToken))
                .retrieve()
                .body(KakaoSocialProfileId.class);
    }

    private String convertToBearerToken(final String accessToken) {
        return BEARER_PREFIX + accessToken;
    }
}
