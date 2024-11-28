package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.exports.OauthLoginService;
import com.project1hour.api.core.application.user.imports.OauthRestClient;
import com.project1hour.api.core.application.user.imports.OauthRestClientFactory;
import com.project1hour.api.core.application.user.imports.OpenIDConnectManager;
import com.project1hour.api.core.application.user.imports.OpenIDConnectManagerFactory;
import com.project1hour.api.core.application.user.imports.UserCommandPort;
import com.project1hour.api.core.application.user.model.TokenPackage;
import com.project1hour.api.core.application.user.model.TokenPair;
import com.project1hour.api.core.application.user.model.UserDetail;
import com.project1hour.api.core.application.user.model.UserSocialInfo;
import com.project1hour.api.core.application.user.model.WebKey;
import com.project1hour.api.core.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OauthLoginUseCase implements OauthLoginService {

    private final TokenIssuanceUseCase tokenIssuanceUseCase;

    private final OauthRestClientFactory oauthRestClientFactory;
    private final OpenIDConnectManagerFactory openIDConnectManagerFactory;
    private final UserCommandPort userCommandPort;


    @Override
    public Response login(final Request request) {
        User authenticatedUser = oauthLogin(request.provider(), request.authorizationCode());

        UserDetail userDetail = new UserDetail(authenticatedUser.getId().id());
        TokenPair tokens = tokenIssuanceUseCase.createTokens(userDetail);
        return new Response(tokens.accessToken(), tokens.refreshToken(), authenticatedUser.isProfileRequired());
    }

    protected User oauthLogin(final String provider, final String authorizationCode) {
        OauthRestClient oauthRestClient = oauthRestClientFactory.getOauthRestClientByProvider(provider);
        OpenIDConnectManager openIDConnectManager = openIDConnectManagerFactory.getOIDCManagerByProvider(provider);

        TokenPackage tokenPackage = oauthRestClient.requestTokenPackageByAuthorizationCode(authorizationCode);
        List<WebKey> webKeys = oauthRestClient.requestWebKeys();
        UserSocialInfo userSocialInfo = openIDConnectManager.parseIdTokenByWebKeys(tokenPackage.idToken(), webKeys);

        return userCommandPort.saveUserOauthInfo(provider, userSocialInfo, tokenPackage)
                .orElseGet(() -> userCommandPort.saveAuthenticatedUser(User.createAuthenticatedUser()));
    }
}
