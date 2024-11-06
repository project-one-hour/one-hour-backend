package com.project1hour.api.core.infrastructure.restclient.model;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public record KakaoRequestTokensBody(
        String grantType,
        String clientId,
        String redirectUri,
        String code
) {

    private static final String STATIC_GRANT_TYPE = "authorization_code";

    public KakaoRequestTokensBody(final String clientId, final String redirectUri, final String code) {
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
