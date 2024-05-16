package com.project1hour.api.global.advice;

public class InfraStructureException extends CustomException {

    public InfraStructureException(final String message, final String errorCode) {
        super(message, errorCode);
    }
}
