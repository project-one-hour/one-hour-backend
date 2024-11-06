package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.exports.TokenAuthenticationFacade;
import com.project1hour.api.core.application.user.imports.TokenAuthManager;
import com.project1hour.api.core.application.user.model.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationUseCase implements TokenAuthenticationFacade {

    private final TokenAuthManager tokenAuthManager;

    @Override
    public UserDetail authenticateUser(final String token) {
        return extractUserDetail(token);
    }

    protected UserDetail extractUserDetail(final String token) {
        return tokenAuthManager.getUserDetail(token);
    }
}
