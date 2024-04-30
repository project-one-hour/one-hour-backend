package com.project1hour.api.auth.application;

import com.project1hour.api.auth.application.dto.SocialInfo;

public interface OauthClient {

    boolean isSupport(String inputProvider);

    SocialInfo requestSocialProfileByToken(String accessToken);
}
