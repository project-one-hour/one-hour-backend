package com.project1hour.api.auth.infrastructure.apple;

import com.project1hour.api.auth.application.OauthClient;
import com.project1hour.api.auth.application.dto.SocialInfo;
import com.project1hour.api.member.domain.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleOauthClient implements OauthClient {

    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public boolean isSupport(final String inputProvider) {
        return Provider.valueOf(inputProvider.toUpperCase())
                .equals(Provider.APPLE);
    }

    @Override
    public SocialInfo requestSocialProfileByToken(final String accessToken) {
        throw new UnsupportedOperationException("애플 로그인은 아직 지원하지 않습니다.");
    }
}
