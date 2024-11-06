package com.project1hour.api.core.application.user.exports;

public interface OauthLoginService {

    Response login(Request request);

    record Request(String provider, String authorizationCode) {
    }

    record Response(String accessToken, String refreshToken, boolean isProfileRequired) {
    }
}
