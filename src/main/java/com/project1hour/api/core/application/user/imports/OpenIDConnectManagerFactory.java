package com.project1hour.api.core.application.user.imports;

import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenIDConnectManagerFactory {

    private final List<OpenIDConnectManager> openIDConnectManagers;

    public OpenIDConnectManager getOIDCManagerByProvider(final String provider) {
        return openIDConnectManagers.stream()
                .filter(manager -> manager.isSupport(provider))
                .findAny()
                .orElseThrow(() -> new NotFoundException("지원하는 소셜 로그인이 없습니다.", ErrorCode.OAUTH_PROVIDER_NOT_FOUND));
    }
}
