package com.project1hour.api.core.implement.member;

import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberReader {

    private final MemberRepository memberRepository;

    public Member read(final Long memberId) {
        return memberRepository.getById(memberId);
    }

    public boolean isExistsNickname(final String nickname) {
        return memberRepository.isExistByNickname(nickname);
    }
}
