package com.project1hour.api.core.application.user.imports;


public interface OauthClient2 {

    boolean isSupport(String provider);

    SocialProfileId findSocialProfileIdByAccessToken(String accessToken);

    interface SocialProfileId {
        String id();
    }
}
