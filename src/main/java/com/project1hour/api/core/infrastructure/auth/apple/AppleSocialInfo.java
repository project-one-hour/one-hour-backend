package com.project1hour.api.core.infrastructure.auth.apple;

import com.project1hour.api.core.domain.auth.SocialInfo;
import com.project1hour.api.core.domain.member.Provider;

public class AppleSocialInfo implements SocialInfo {

    private final String providerId;
    private String email;

    public AppleSocialInfo(final String providerId, final String email) {
        this.providerId = providerId;
        this.email = email;
    }

    @Override
    public Provider getProvider() {
        return Provider.APPLE;
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
