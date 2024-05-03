package com.project1hour.api.core.implement.auth;

import com.project1hour.api.core.domain.auth.OauthClient;
import com.project1hour.api.core.exception.auth.OauthProviderNotFound;
import com.project1hour.api.core.implement.auth.dto.SocialInfo;
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
                .orElseThrow(OauthProviderNotFound::new);

        return oauthClient.requestSocialProfileByToken(token);
    }
}
