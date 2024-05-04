package com.project1hour.api.global.advice;

import org.springframework.http.HttpStatus;

public class InfraStructureException extends CustomException {

    public InfraStructureException(final String message, final String errorCode) {
        super(message, errorCode, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
