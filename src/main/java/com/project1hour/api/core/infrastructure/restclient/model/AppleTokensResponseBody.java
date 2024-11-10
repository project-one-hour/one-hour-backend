package com.project1hour.api.core.infrastructure.restclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project1hour.api.core.application.user.model.TokenPackage;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AppleTokensResponseBody(
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("id_token") String idToken,
        @JsonProperty("expires_in") int accessTokenExpiresIn,
        @JsonProperty("refresh_token") String refreshToken
) implements TokenPackage {

    private static final int REFRESH_TOKEN_NO_EXPIRATION = -1;

    public int refreshTokenExpiresIn() {
        return REFRESH_TOKEN_NO_EXPIRATION;
    }
}
