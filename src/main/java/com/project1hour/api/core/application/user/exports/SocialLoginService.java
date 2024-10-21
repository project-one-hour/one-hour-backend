package com.project1hour.api.core.application.user.exports;

public interface SocialLoginService {

    Long login(Request request);

    record Request(String provider, String accessToken) {
    }
}
