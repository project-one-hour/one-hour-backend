package com.project1hour.api.core.implement.member;

import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.domain.member.MemberRepository;
import com.project1hour.api.core.implement.auth.AuthProcessor;
import com.project1hour.api.core.implement.auth.dto.SocialInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberProcessor {

    private final AuthProcessor authProcessor;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createJustAuthenticatedMember(final SocialInfo socialInfo) {
        Member member = Member.createJustAuthenticatedMember();
        memberRepository.save(member);
        authProcessor.save(member, socialInfo);
        return member.getId();
    }
}
