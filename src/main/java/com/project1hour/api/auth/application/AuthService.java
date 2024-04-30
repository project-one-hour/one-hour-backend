package com.project1hour.api.auth.application;

import com.project1hour.api.auth.application.dto.SocialInfo;
import com.project1hour.api.auth.application.dto.TokenResponse;
import com.project1hour.api.auth.exception.OauthProviderNotFound;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final List<OauthClient> oauthClients;

    public TokenResponse createToken(final String provider, final String token) {
        SocialInfo socialInfo = findSocialInfo(provider, token);

        return null;
    }

    private SocialInfo findSocialInfo(final String provider, final String token) {
        OauthClient oauthClient = oauthClients.stream()
                .filter(client -> client.isSupport(provider))
                .findAny()
                .orElseThrow(OauthProviderNotFound::new);

        return oauthClient.requestSocialProfileByToken(token);
    }
}
