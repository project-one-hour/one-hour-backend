package com.project1hour.api.global.advice;

public enum ErrorCode {

    // Auth 관련
    OAUTH_PROVIDER_NOT_FOUND("A001", "입력받은 inputProvider에 해당하는 Provider가 존재하지 않는 경우"),

    // Infrastructure 관련
    CAN_NOT_EXCHANGE_OAUTH_PROFILE("I000", "Oauth2 사용자의 프로필을 요청할 수 없는 경우"),

    // Global 예외 관련
    METHOD_ARGUMENT_NOT_VALID("G000", "요청에 대한 DTO 필드의 값이 NULL인 경우"),
    ;

    private final String errorCode;
    private final String description;

    ErrorCode(final String errorCode, final String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
