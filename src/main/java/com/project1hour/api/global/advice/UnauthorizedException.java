package com.project1hour.api.global.advice;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException(final String message, final String errorCode) {
        super(message, errorCode);
    }
}
