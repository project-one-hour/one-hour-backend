package com.project1hour.api.core.infrastructure.auth.apple;

import static com.project1hour.api.core.domain.member.Provider.APPLE;

import com.project1hour.api.core.domain.auth.OauthClient;
import com.project1hour.api.core.implement.auth.dto.SocialInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleOauthClient implements OauthClient {

    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public boolean isSupport(final String inputProvider) {
        return APPLE.name()
                .equals(inputProvider.toUpperCase());
    }

    @Override
    public SocialInfo requestSocialProfileByToken(final String accessToken) {
        throw new UnsupportedOperationException("애플 로그인은 아직 지원하지 않습니다.");
    }
}
