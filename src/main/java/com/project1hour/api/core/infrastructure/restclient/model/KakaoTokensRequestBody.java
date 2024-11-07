package com.project1hour.api.core.infrastructure.restclient.model;

import static com.project1hour.api.core.application.user.imports.OauthRestClient.STATIC_GRANT_TYPE;

import lombok.Builder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public record KakaoTokensRequestBody(
        String grantType,
        String clientId,
        String redirectUri,
        String code
) {

    @Builder
    public KakaoTokensRequestBody(final String clientId, final String redirectUri, final String code) {
        this(STATIC_GRANT_TYPE, clientId, redirectUri, code);
    }

    public MultiValueMap<String, String> toMultiValueMap() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("grant_type", grantType);
        multiValueMap.add("client_id", clientId);
        multiValueMap.add("redirect_uri", redirectUri);
        multiValueMap.add("code", code);
        return multiValueMap;
    }
}
