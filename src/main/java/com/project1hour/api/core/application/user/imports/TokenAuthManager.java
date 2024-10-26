package com.project1hour.api.core.application.user.imports;


import com.project1hour.api.core.application.user.model.UserDetail;

public interface TokenAuthManager {

    String createToken(UserDetail userDetail);

    UserDetail getUserDetail(String token);
}
