package com.project1hour.api.global.advice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestException extends BusinessException {

    public BadRequestException(final String message, final ErrorCode errorCode) {
        super(message, BAD_REQUEST.value(), errorCode.getErrorCode());
    }
}
