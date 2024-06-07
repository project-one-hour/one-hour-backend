package com.project1hour.api.core.domain.auth;

import com.project1hour.api.core.domain.member.Provider;

public interface SocialInfo {

    Provider getProvider();

    String getProviderId();

    String getEmail();
}
