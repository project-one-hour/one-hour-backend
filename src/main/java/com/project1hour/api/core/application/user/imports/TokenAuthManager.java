package com.project1hour.api.core.application.user.imports;


import com.project1hour.api.core.application.user.model.UserDetail;

public interface TokenAuthManager {

    String createAccessToken(UserDetail userDetail);

    String createRefreshToken(Long userId);

    UserDetail getUserDetail(String token);
}
