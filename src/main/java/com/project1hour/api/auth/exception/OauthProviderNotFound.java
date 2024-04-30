package com.project1hour.api.auth.exception;

import static com.project1hour.api.global.advice.ErrorCode.OAUTH_PROVIDER_NOT_FOUND;

import com.project1hour.api.global.advice.NotFoundException;

public class OauthProviderNotFound extends NotFoundException {

    private static final String MESSAGE = "지원하는 소셜 로그인이 없습니다.";

    public OauthProviderNotFound() {
        super(MESSAGE, OAUTH_PROVIDER_NOT_FOUND.getErrorCode());
    }
}
