package com.project1hour.api.global.advice;


import static com.project1hour.api.global.advice.GlobalErrorCode.METHOD_ARGUMENT_NOT_VALID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getFieldErrors()
                .getFirst()
                .getDefaultMessage();

        log.error("ERROR CODE {} : {}", METHOD_ARGUMENT_NOT_VALID.getErrorCode(), errorMessage);

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(errorMessage, METHOD_ARGUMENT_NOT_VALID.getErrorCode()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error("ERROR CODE {} : {}", e.getErrorCode(), e.getMessage(), e);

        return ResponseEntity.status(e.getStatusCode())
                .body(ErrorResponse.of(e.getMessage(), e.getErrorCode()));
    }
}
