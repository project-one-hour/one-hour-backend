package com.project1hour.api.core.infrastructure.member.jpa;

import com.project1hour.api.core.domain.member.MemberInterest;
import com.project1hour.api.core.domain.member.MemberInterestRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CoreMemberInterestRepository implements MemberInterestRepository {

    private final JpaMemberInterestRepository jpaMemberInterestRepository;

    @Override
    public List<MemberInterest> saveAll(final List<MemberInterest> memberInterests) {
        return jpaMemberInterestRepository.saveAll(memberInterests);
    }
}
