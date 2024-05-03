package com.project1hour.api.core.exception.auth;

import static com.project1hour.api.global.advice.ErrorCode.MALFORMED_TOKEN;

import com.project1hour.api.global.advice.BadRequestException;

public class MalformedTokenException extends BadRequestException {

    public MalformedTokenException() {
        super("유효하지 않은 토큰입니다.", MALFORMED_TOKEN.getErrorCode());
    }
}
