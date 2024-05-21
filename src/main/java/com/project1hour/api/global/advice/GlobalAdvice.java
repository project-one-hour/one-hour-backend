package com.project1hour.api.global.advice;


import static com.project1hour.api.global.advice.ErrorCode.METHOD_ARGUMENT_NOT_VALID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String errorMessage = e.getFieldErrors()
                .getFirst()
                .getDefaultMessage();

        log.warn("ERROR CODE {} : {}", METHOD_ARGUMENT_NOT_VALID.getErrorCode(), errorMessage);
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(errorMessage, METHOD_ARGUMENT_NOT_VALID.getErrorCode()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException e) {
        log.warn("ERROR CODE {} : {}", e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.of(e.getMessage(), e.getErrorCode()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException e) {
        log.warn("ERROR CODE {} : {}", e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(e.getStatusCode())
                .body(ErrorResponse.of(e.getMessage(), e.getErrorCode()));
    }

    @ExceptionHandler(InfraStructureException.class)
    public ResponseEntity<ErrorResponse> handleInfraStructure(InfraStructureException e) {
        log.warn("ERROR CODE {} : {}", e.getErrorCode(), e.getMessage());
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.of(e.getMessage(), e.getErrorCode()));
    }
}
