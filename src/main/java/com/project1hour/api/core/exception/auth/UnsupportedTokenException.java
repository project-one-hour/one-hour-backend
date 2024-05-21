package com.project1hour.api.core.exception.auth;

import static com.project1hour.api.global.advice.ErrorCode.UNSUPPORTED_TOKEN;

import com.project1hour.api.global.advice.InfraStructureException;

public class UnsupportedTokenException extends InfraStructureException {

    public UnsupportedTokenException() {
        super("지원하지 않는 토큰 형식입니다.", UNSUPPORTED_TOKEN);
    }
}
