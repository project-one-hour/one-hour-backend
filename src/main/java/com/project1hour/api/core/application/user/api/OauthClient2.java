package com.project1hour.api.core.application.user.api;


public interface OauthClient2 {

    boolean isSupport(String provider);

    SocialProfileId findSocialProfileIdByAccessToken(String accessToken);
}
