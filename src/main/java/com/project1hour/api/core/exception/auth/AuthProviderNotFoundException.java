package com.project1hour.api.core.exception.auth;

import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.NotFoundException;

public class AuthProviderNotFoundException extends NotFoundException {

    private static final String MESSAGE = "Auth 정보를 찾을 수 없습니다.";

    public AuthProviderNotFoundException() {
        super(MESSAGE, ErrorCode.AUTH_PROVIDER_NOT_FOUND.getErrorCode());
    }
}
