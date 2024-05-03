package com.project1hour.api.core.application.auth;

import com.project1hour.api.core.implement.auth.AuthReader;
import com.project1hour.api.core.implement.auth.SocialProfileReader;
import com.project1hour.api.core.implement.auth.TokenProcessor;
import com.project1hour.api.core.implement.auth.dto.SocialInfo;
import com.project1hour.api.core.implement.auth.dto.TokenResponse;
import com.project1hour.api.core.implement.member.MemberProcessor;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthReader authReader;
    private final MemberProcessor memberProcessor;
    private final TokenProcessor tokenProcessor;
    private final SocialProfileReader socialInfoReader;

    public TokenResponse createToken(final String provider, final String token) {
        SocialInfo socialInfo = socialInfoReader.read(provider, token);

        Optional<Long> optionalMemberId = authReader.findExistsMemberId(socialInfo.getProviderId());
        if (optionalMemberId.isPresent()) {
            return tokenProcessor.createMemberToken(optionalMemberId.get(), false);
        }
        Long newMemberId = memberProcessor.createJustAuthenticatedMember(socialInfo);
        return tokenProcessor.createMemberToken(newMemberId, true);
    }
}
