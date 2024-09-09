package com.project1hour.api.core.application.user.manager;

public interface TokenAuthManager {

    String authorize(UserDetail userDetail);

    boolean isAuthenticated(String token);

    UserDetail getUserDetail(String token);

    record UserDetail(Long userId) {
    }
}
