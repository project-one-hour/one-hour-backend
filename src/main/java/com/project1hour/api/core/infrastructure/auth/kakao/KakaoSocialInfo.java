package com.project1hour.api.core.infrastructure.auth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project1hour.api.core.domain.auth.SocialInfo;
import com.project1hour.api.core.domain.member.Provider;
import java.util.Map;

public class KakaoSocialInfo implements SocialInfo {

    @JsonProperty("id")
    private final String providerId;
    private String email;

    public KakaoSocialInfo(final String providerId, final String email) {
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
