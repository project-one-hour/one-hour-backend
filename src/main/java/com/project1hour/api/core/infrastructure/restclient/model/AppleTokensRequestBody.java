package com.project1hour.api.core.infrastructure.restclient.model;

import static com.project1hour.api.core.application.user.imports.OauthRestClient.STATIC_GRANT_TYPE;

import lombok.Builder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public record AppleTokensRequestBody(
        String grantType,
        String clientId,
        String redirectUri,
        String code,
        String clientSecret
) {

    @Builder
    public AppleTokensRequestBody(final String clientId, final String redirectUri,
                                  final String code, final String clientSecret) {
        this(STATIC_GRANT_TYPE, clientId, redirectUri, code, clientSecret);
    }

    public MultiValueMap<String, String> toMultiValueMap() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("grant_type", grantType);
        multiValueMap.add("client_id", clientId);
        multiValueMap.add("redirect_uri", redirectUri);
        multiValueMap.add("code", code);
        multiValueMap.add("client_secret", clientSecret);
        return multiValueMap;
    }
}
