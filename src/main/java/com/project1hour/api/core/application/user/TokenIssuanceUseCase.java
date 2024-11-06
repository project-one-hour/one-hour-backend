package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.imports.TokenAuthManager;
import com.project1hour.api.core.application.user.model.TokenPair;
import com.project1hour.api.core.application.user.model.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenIssuanceUseCase {

    private final TokenAuthManager tokenAuthManager;

    protected TokenPair createTokens(final UserDetail userDetail) {
        String accessToken = tokenAuthManager.createAccessToken(userDetail);
        String refreshToken = tokenAuthManager.createRefreshToken(userDetail.userId());
        return new TokenPair(accessToken, refreshToken);
    }
}
