package com.project1hour.api.core.infrastructure.restclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project1hour.api.core.application.user.imports.OauthClient2.SocialProfileId;

public record KakaoSocialProfileId(
        @JsonProperty("id") String id) implements SocialProfileId {
}
