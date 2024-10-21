package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.data.UserDetail;
import com.project1hour.api.core.application.user.imports.TokenAuthManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationUseCase {

    private final TokenAuthManager tokenAuthManager;

    protected String createAccessToken(final Long userId) {
        UserDetail userDetail = new UserDetail(userId);
        return tokenAuthManager.createToken(userDetail);
    }
}
