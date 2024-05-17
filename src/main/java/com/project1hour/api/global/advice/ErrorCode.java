package com.project1hour.api.global.advice;

public enum ErrorCode {

    // Auth 관련
    OAUTH_PROVIDER_NOT_FOUND("A000", "요청으로 넘겨준 provider에 해당하는 Social Provider가 일치하지 않는 경우"),
    AUTH_PROVIDER_NOT_FOUND("A001", "기존 가입자의 소셜로그인 정보를 찾을 수 없는 경우"),
    AUTH_TOKEN_NOT_FOUND("A002", "요청 헤더에 JWT 토큰이 없는 경우 (Bearer xxx)"),
    UNAUTHORIZED_PATH("A003", "해당 API에 접근할 수 있는 권한이 없는 사용자의 경우"),

    MEMBER_NOT_FOUND("M001", "사용자 조회 시, 입력 받은 id의 사용자가 존재하지 않는 경우"),

    // Infrastructure 관련
    CAN_NOT_EXCHANGE_OAUTH_PROFILE("I000", "Oauth2 사용자의 프로필을 요청할 수 없는 경우"),
    INVALID_TOKEN_SIGNATURE("I001", "JWT 토큰 시그니처가 잘못된 경우"),
    UNSUPPORTED_TOKEN("I002", "토큰 형식이 JWT가 아닌 경우"),
    EXPIRED_TOKEN("I003", "JWT 토큰이 만료된 경우"),
    MALFORMED_TOKEN("I004", "유효하지 않은 JWT 토큰인 경우"),

    // Global 예외 관련
    METHOD_ARGUMENT_NOT_VALID("G000", "request-fields의 값이 NULL인 경우");

    private final String errorCode;
    private final String description;

    ErrorCode(final String errorCode, final String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }
}
