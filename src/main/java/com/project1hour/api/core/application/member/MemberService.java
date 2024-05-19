package com.project1hour.api.core.application.member;

import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.implement.member.MemberInterestProcessor;
import com.project1hour.api.core.implement.member.MemberProcessor;
import com.project1hour.api.core.implement.member.MemberReader;
import com.project1hour.api.core.implement.member.dto.NewMemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberReader memberReader;
    private final MemberProcessor memberProcessor;
    private final MemberInterestProcessor memberInterestProcessor;

    public void signUp(final Long authenticatedMemberId, final NewMemberInfo newMemberInfo) {
        Member member = memberReader.read(authenticatedMemberId);
        memberProcessor.signUpMember(member, newMemberInfo);
        memberInterestProcessor.saveAll(newMemberInfo.interests(), member);
    }

    public boolean isDuplicate(final String nickname) {
        return memberReader.isExistsNickname(nickname);
    }
}
