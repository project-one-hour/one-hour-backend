package com.project1hour.api.core.infrastructure.auth.kakao;

import static com.project1hour.api.core.domain.member.Provider.KAKAO;

import com.project1hour.api.core.domain.auth.OauthClient;
import com.project1hour.api.core.implement.auth.dto.SocialInfo;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoOauthClient implements OauthClient {

    private static final String BEARER_PREFIX = "Bearer ";

    private final String profileUrl;
    private final KakaoApiFetcher kakaoApiFetcher;

    public KakaoOauthClient(@Value("${oauth2.kakao.url.profile}") final String profileUrl,
                            final KakaoApiFetcher kakaoApiFetcher) {
        this.profileUrl = profileUrl;
        this.kakaoApiFetcher = kakaoApiFetcher;
    }

    @Override
    public boolean isSupport(final String inputProvider) {
        return KAKAO.name()
                .equals(inputProvider.toUpperCase());
    }

    @Override
    public SocialInfo requestSocialProfileByToken(final String accessToken) {
        URI uri = URI.create(profileUrl);
        return kakaoApiFetcher.fetchKakaoUserInfo(uri, BEARER_PREFIX + accessToken);
    }
}
