package com.project1hour.api.global.advice;

public enum GlobalErrorCode {

    METHOD_ARGUMENT_NOT_VALID("G1000"),
    OAUTH_PROVIDER_NOT_FOUND("G1001");

    private String errorCode;

    GlobalErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
