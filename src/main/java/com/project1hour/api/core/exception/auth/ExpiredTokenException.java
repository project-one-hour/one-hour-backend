package com.project1hour.api.core.exception.auth;

import static com.project1hour.api.global.advice.ErrorCode.EXPIRED_TOKEN;

import com.project1hour.api.global.advice.InfraStructureException;

public class ExpiredTokenException extends InfraStructureException {

    public ExpiredTokenException() {
        super("만료된 토큰입니다.", EXPIRED_TOKEN);
    }
}
