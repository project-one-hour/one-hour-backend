package com.project1hour.api.core.infrastructure.restclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project1hour.api.core.application.user.model.WebKey;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoResponseJWKsBody(
        @JsonProperty("keys") List<KakaoResponseJWKBody> keys
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record KakaoResponseJWKBody(
            @JsonProperty("kid") String keyId,
            @JsonProperty("kty") String keyType,
            @JsonProperty("alg") String algorithm,
            @JsonProperty("use") String use,
            @JsonProperty("n") String modulus,
            @JsonProperty("e") String exponent
    ) implements WebKey {
    }
}
