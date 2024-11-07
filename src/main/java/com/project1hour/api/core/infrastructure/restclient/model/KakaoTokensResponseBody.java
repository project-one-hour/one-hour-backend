package com.project1hour.api.core.infrastructure.restclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project1hour.api.core.application.user.model.TokenPackage;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoTokensResponseBody(
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("id_token") String idToken,
        @JsonProperty("expires_in") int accessTokenExpiresIn,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("refresh_token_expires_in") int refreshTokenExpiresIn,
        @JsonProperty("scope") String scope
) implements TokenPackage {
}
