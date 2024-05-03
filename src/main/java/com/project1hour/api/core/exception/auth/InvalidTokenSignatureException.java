package com.project1hour.api.core.exception.auth;

import static com.project1hour.api.global.advice.ErrorCode.INVALID_TOKEN_SIGNATURE;

import com.project1hour.api.global.advice.InfraStructureException;

public class InvalidTokenSignatureException extends InfraStructureException {

    public InvalidTokenSignatureException() {
        super("잘못된 토큰 시그니처 입니다.", INVALID_TOKEN_SIGNATURE.getErrorCode());
    }
}
