package com.project1hour.api.core.implement.member;

import com.project1hour.api.core.domain.interest.Interest;
import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.domain.member.MemberInterest;
import com.project1hour.api.core.domain.member.MemberInterestRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class MemberInterestProcessor {

    private final MemberInterestRepository memberInterestRepository;

    public void saveAll(final List<String> interestNames, final Member member) {
        List<Interest> interests = Interest.findAllByNames(interestNames);
        List<MemberInterest> newMemberInterests = MemberInterest.createNewMemberInterests(interests, member);
        memberInterestRepository.saveAll(newMemberInterests);
    }
}
