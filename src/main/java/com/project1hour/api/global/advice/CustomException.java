package com.project1hour.api.global.advice;

public class CustomException extends RuntimeException {

    private final int statusCode;
    private final String errorCode;

    public CustomException(final String message, final String errorCode, final int statusCode) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
