package com.project1hour.api.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project1hour.api.member.domain.Provider;
import java.util.Map;

public class KakaoSocialInfoResponse implements SocialInfo {

    @JsonProperty("id")
    private final String providerId;
    private String email;

    public KakaoSocialInfoResponse(final String providerId, final String email) {
        this.providerId = providerId;
        this.email = email;
    }

    @JsonProperty("kakao_account")
    private void unpackNested(final Map<String, Object> kakaoAccount) {
        this.email = (String) kakaoAccount.get("email");
    }

    @Override
    public Provider getProvider() {
        return Provider.KAKAO;
    }

    @Override
    public String getProviderId() {
        return providerId;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
