package com.project1hour.api.core.implement.auth.dto;

import com.project1hour.api.core.domain.member.Provider;

public interface SocialInfo {

    Provider getProvider();

    String getProviderId();

    String getEmail();
}
