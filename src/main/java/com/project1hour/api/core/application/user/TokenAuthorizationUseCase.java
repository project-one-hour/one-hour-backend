package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.data.UserDetail;
import com.project1hour.api.core.application.user.exports.TokenAuthorizationFacade;
import com.project1hour.api.core.application.user.imports.TokenAuthManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenAuthorizationUseCase implements TokenAuthorizationFacade {

    private final TokenAuthManager tokenAuthManager;

    @Override
    public UserDetail authenticateUser(final String token) {
        return authenticate(token);
    }

    protected UserDetail authenticate(final String token) {
        return tokenAuthManager.getUserDetail(token);
    }
}
