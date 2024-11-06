package com.project1hour.api.core.application.user.model;

public interface TokenPackage {

    String idToken();

    String accessToken();

    int accessTokenExpiresIn();

    String refreshToken();

    int refreshTokenExpiresIn();
}
