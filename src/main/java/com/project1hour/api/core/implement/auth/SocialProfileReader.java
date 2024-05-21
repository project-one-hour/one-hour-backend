package com.project1hour.api.core.implement.auth;

import com.project1hour.api.core.domain.auth.OauthClient;
import com.project1hour.api.core.implement.auth.dto.SocialInfo;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocialProfileReader {

    private final List<OauthClient> oauthClients;

    public SocialInfo read(final String provider, final String token) {
        OauthClient oauthClient = oauthClients.stream()
                .filter(client -> client.isSupport(provider))
                .findAny()
                .orElseThrow(() -> new NotFoundException("지원하는 소셜 로그인이 없습니다.", ErrorCode.OAUTH_PROVIDER_NOT_FOUND));

        return oauthClient.requestSocialProfileByToken(token);
    }
}
