package com.project1hour.api.core.application.user.exports;

import com.project1hour.api.core.application.user.model.UserDetail;

public interface TokenAuthenticationFacade {

    UserDetail authenticateUser(String token);
}
