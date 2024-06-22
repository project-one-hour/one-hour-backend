package com.project1hour.api.core.implement.member;

import com.project1hour.api.core.domain.auth.SocialInfo;
import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.domain.member.MemberRepository;
import com.project1hour.api.core.implement.auth.AuthProcessor;
import com.project1hour.api.core.implement.member.dto.NewMemberInfo;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class MemberProcessor {

    private final MemberInterestProcessor memberInterestProcessor;
    private final AuthProcessor authProcessor;
    private final MemberRepository memberRepository;

    public Long saveJustAuthenticatedMember(final SocialInfo socialInfo) {
        Member member = Member.createJustAuthenticatedMember();
        memberRepository.save(member);
        authProcessor.save(member, socialInfo);

        return member.getId();
    }

    public void signUpMember(final Member member, final NewMemberInfo newMemberInfo) {
        if (member.isAlreadySignedUp()) {
            throw new BadRequestException("이미 가입한 사용자 입니다.", ErrorCode.DUPLICATED_SIGN_UP);
        }
        Member signedUpMember = member.signUp(
                newMemberInfo.nickname(),
                newMemberInfo.gender(),
                newMemberInfo.birthday(),
                newMemberInfo.mbti(),
                newMemberInfo.isAllowingMarketingInfo()
        );

        Member savedMember = memberRepository.save(signedUpMember);
        memberInterestProcessor.saveAll(newMemberInfo.interests(), savedMember);
    }

    public void updateNewProfileImages(final Member member, final List<String> imageUrls) {
        Member updatedMember = member.addNewProfileImages(imageUrls);
        memberRepository.save(updatedMember);
    }
}
