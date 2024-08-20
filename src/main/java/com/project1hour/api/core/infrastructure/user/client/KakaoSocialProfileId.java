package com.project1hour.api.core.infrastructure.user.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project1hour.api.core.application.user.api.SocialProfileId;

public record KakaoSocialProfileId(
        @JsonProperty("id") String id) implements SocialProfileId {
}
