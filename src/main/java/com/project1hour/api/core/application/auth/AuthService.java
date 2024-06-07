package com.project1hour.api.core.application.auth;

import com.project1hour.api.core.domain.auth.SocialInfo;
import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.implement.auth.AuthProcessor;
import com.project1hour.api.core.implement.auth.AuthReader;
import com.project1hour.api.core.implement.auth.SocialProfileReader;
import com.project1hour.api.core.implement.auth.TokenProcessor;
import com.project1hour.api.core.implement.auth.dto.TokenResponse;
import com.project1hour.api.core.implement.member.MemberProcessor;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthReader authReader;
    private final AuthProcessor authProcessor;
    private final MemberProcessor memberProcessor;
    private final TokenProcessor tokenProcessor;
    private final SocialProfileReader socialInfoReader;

    public TokenResponse createToken(final String provider, final String token) {
        SocialInfo socialInfo = socialInfoReader.read(provider, token);
        Optional<Member> existsMember = authReader.readExistsMember(socialInfo);

        return existsMember.map(member -> createExistsMemberToken(member, socialInfo))
                .orElseGet(() -> createNewMemberToken(socialInfo));
    }

    private TokenResponse createExistsMemberToken(final Member member, final SocialInfo socialInfo) {
        authProcessor.updateAuthProfile(socialInfo);
        return tokenProcessor.createMemberToken(member.getId(), false);
    }

    private TokenResponse createNewMemberToken(final SocialInfo socialInfo) {
        Long newMemberId = memberProcessor.saveJustAuthenticatedMember(socialInfo);
        return tokenProcessor.createMemberToken(newMemberId, true);
    }
}
