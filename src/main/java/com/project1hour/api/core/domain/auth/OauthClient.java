package com.project1hour.api.core.domain.auth;

import com.project1hour.api.core.implement.auth.dto.SocialInfo;

public interface OauthClient {

    boolean isSupport(String inputProvider);

    SocialInfo requestSocialProfileByToken(String accessToken);
}
