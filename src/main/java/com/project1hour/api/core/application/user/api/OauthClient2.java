package com.project1hour.api.core.application.user.api;


import com.project1hour.api.core.application.user.api.data.SocialProfileId;

public interface OauthClient2 {

    boolean isSupport(String provider);

    SocialProfileId findSocialProfileIdByAccessToken(String accessToken);
}
