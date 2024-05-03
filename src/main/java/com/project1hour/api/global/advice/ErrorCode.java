package com.project1hour.api.global.advice;

public enum ErrorCode {

    // Auth 관련
    OAUTH_PROVIDER_NOT_FOUND("A000", "입력받은 inputProvider에 해당하는 Provider가 존재하지 않는 경우"),
    AUTH_PROVIDER_NOT_FOUND("A001", "찾고자 하는 AuthProvider를 찾을 수 없는 경우"),

    // Member 관련
    MEMBER_NOT_FOUND("M000", "회원을 찾을 수 없는 경우"),

    // Infrastructure 관련
    CAN_NOT_EXCHANGE_OAUTH_PROFILE("I000", "Oauth2 사용자의 프로필을 요청할 수 없는 경우"),
    INVALID_TOKEN_SIGNATURE("I001", "JWT 토큰 시그니처가 잘못된 경우"),
    UNSUPPORTED_TOKEN("I002", "토큰 형식이 JWT가 아닌 경우"),
    EXPIRED_TOKEN("I003", "JWT 토큰이 만료된 경우"),
    MALFORMED_TOKEN("I004", "유효하지 않은 JWT 토큰인 경우"),

    // Global 예외 관련
    METHOD_ARGUMENT_NOT_VALID("G000", "요청에 대한 DTO 필드의 값이 NULL인 경우");

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
