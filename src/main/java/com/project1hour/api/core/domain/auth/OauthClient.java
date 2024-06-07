package com.project1hour.api.core.domain.auth;

public interface OauthClient {

    boolean isSupport(String inputProvider);

    SocialInfo requestSocialProfileByToken(String accessToken);
}
