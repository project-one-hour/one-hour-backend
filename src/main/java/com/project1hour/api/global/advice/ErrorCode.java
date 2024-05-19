package com.project1hour.api.global.advice;

public enum ErrorCode {

    // Auth 관련
    OAUTH_PROVIDER_NOT_FOUND("A000", "소셜로그인 시, 요청으로 넘겨준 provider에 해당하는 Social Provider가 일치하지 않는 경우"),
    AUTH_PROVIDER_NOT_FOUND("A001", "소셜로그인 시, 기존 가입자의 소셜로그인 정보를 찾을 수 없는 경우"),
    AUTH_TOKEN_NOT_FOUND("A002", "API 요청 시, 요청 헤더에 JWT 토큰이 없는 경우 (Bearer jwt.token.here)"),
    UNAUTHORIZED_PATH("A003", "API요청 시, 해당 API에 접근할 수 있는 권한이 없는 사용자의 경우"),

    // Member 관련
    MEMBER_NOT_FOUND("M000", "사용자 조회 시, 입력 받은 id의 사용자가 존재하지 않는 경우"),
    DUPLICATED_SIGN_UP("M001", "회원가입 시, 가입하려는 사용자가 이미 가입을 완료한 상태인 경우"),
    INVALID_NICKNAME_LENGTH("M002", "회원가입 시, 닉네임의 길이가 2글자 이상 8글자 이하가 아닐때"),
    INVALID_NICKNAME_FORMAT("M003", "회원가입 시, 닉네임의 형식이 (한글음절/숫자[옵션]) 혹은 (한글/영어) 혹은 (한글/영어/숫자) 조합으로 이루어져 있지 않은 경우"),
    INVALID_AGE_LIMIT("M004", "회원가입 시, 사용자가 올해 기준 법정 성인 연령이 아닌 경우"),
    INVALID_GENDER_VALUE("M005", "회원가입 시, 성별을 찾을 수 없는 경우 ('MALE', 'FEMALE' 만 가능)"),
    INVALID_MBTI_VALUE("M006", "회원가입 시, MBTI가 잘못 입력된 경우"),

    // Interest 관련
    INCLUDE_NOT_EXISTS_INTEREST("R001", "회원가입 시, 입력한 5개의 관심사 중에 존재하지 않는 관심사가 포함된 경우"),

    // Infrastructure 관련
    CAN_NOT_EXCHANGE_OAUTH_PROFILE("I000", "Oauth2 사용자의 프로필을 요청할 수 없는 경우(Provider 서버 예외)"),
    INVALID_TOKEN_SIGNATURE("I001", "JWT 검증 시, JWT 토큰 시그니처가 잘못된 경우"),
    UNSUPPORTED_TOKEN("I002", "JWT 검증 시, 토큰 형식이 JWT가 아닌 경우"),
    EXPIRED_TOKEN("I003", "JWT 검증 시, JWT 토큰이 만료된 경우"),
    MALFORMED_TOKEN("I004", "JWT 검증 시, 유효하지 않은 JWT 토큰인 경우"),

    // Global 예외 관련
    METHOD_ARGUMENT_NOT_VALID("G000", "API 요청 시, request-fields의 값이 NULL인 경우");

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
