package com.project1hour.api.core.application.user.api;

import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OauthClientFactory {

    private final List<OauthClient2> oauthClients;

    public OauthClient2 getOauthClientByProvider(String provider) {
        return oauthClients.stream()
                .filter(client -> client.isSupport(provider))
                .findAny()
                .orElseThrow(() -> new NotFoundException("지원하는 소셜 로그인이 없습니다.", ErrorCode.OAUTH_PROVIDER_NOT_FOUND));
    }
}
