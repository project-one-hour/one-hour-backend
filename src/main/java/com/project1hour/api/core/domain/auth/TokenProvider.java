package com.project1hour.api.core.domain.auth;

import com.project1hour.api.core.domain.member.Authority;

public interface TokenProvider {

    String generateAccessToken(String subject, Authority authority);

    String extractSubject(String token);

    Authority extractAuthority(String token);
}
