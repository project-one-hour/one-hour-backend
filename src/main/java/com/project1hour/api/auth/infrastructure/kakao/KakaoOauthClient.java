package com.project1hour.api.auth.infrastructure.kakao;

import com.project1hour.api.auth.application.OauthClient;
import com.project1hour.api.auth.application.dto.SocialInfo;
import com.project1hour.api.member.domain.Provider;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoOauthClient implements OauthClient {

    private static final String BEARER_PREFIX = "Bearer ";

    private final KakaoApiFetcher kakaoApiFetcher;
    private final String profileUrl;

    public KakaoOauthClient(final KakaoApiFetcher kakaoApiFetcher,
                            @Value("${oauth2.kakao.url.profile}") final String profileUrl) {
        this.kakaoApiFetcher = kakaoApiFetcher;
        this.profileUrl = profileUrl;
    }

    @Override
    public boolean isSupport(final String inputProvider) {
        return Provider.valueOf(inputProvider.toUpperCase())
                .equals(Provider.KAKAO);
    }

    @Override
    public SocialInfo requestSocialProfileByToken(final String accessToken) {
        URI uri = URI.create(profileUrl);
        return kakaoApiFetcher.fetchKakaoUserInfo(uri, BEARER_PREFIX + accessToken);
    }
}
