package com.project1hour.api.auth.application.dto;

import com.project1hour.api.member.domain.Provider;

public interface SocialInfo {

    Provider getProvider();

    String getProviderId();

    String getEmail();
}
