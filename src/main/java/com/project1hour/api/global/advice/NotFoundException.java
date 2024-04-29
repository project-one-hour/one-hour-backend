package com.project1hour.api.global.advice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends BusinessException {

    public NotFoundException(final String message, final String errorCode) {
        super(message, NOT_FOUND.value(), errorCode);
    }
}
