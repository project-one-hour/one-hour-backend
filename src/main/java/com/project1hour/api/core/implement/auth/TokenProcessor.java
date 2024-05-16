package com.project1hour.api.core.implement.auth;

import com.project1hour.api.core.domain.auth.TokenProvider;
import com.project1hour.api.core.domain.member.Authority;
import com.project1hour.api.core.implement.auth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenProcessor {

    private final TokenProvider tokenProvider;

    public TokenResponse createMemberToken(final Long memberId, final boolean isNewMember) {
        return new TokenResponse(
                tokenProvider.generateAccessToken(String.valueOf(memberId), Authority.MEMBER),
                isNewMember
        );
    }
}
