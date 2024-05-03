package com.project1hour.api.core.domain.auth;

public interface TokenProvider {

    String generateAccessToken(String subject, Authority authority);

    Long extractAuthInfo(String token);
}
