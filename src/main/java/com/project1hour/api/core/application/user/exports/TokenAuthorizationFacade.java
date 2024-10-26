package com.project1hour.api.core.application.user.exports;

import com.project1hour.api.core.application.user.model.UserDetail;

public interface TokenAuthorizationFacade {

    UserDetail authenticateUser(String token);
}
