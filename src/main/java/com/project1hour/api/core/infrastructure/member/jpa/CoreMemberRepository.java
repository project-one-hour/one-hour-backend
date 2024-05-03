package com.project1hour.api.core.infrastructure.member.jpa;

import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CoreMemberRepository implements MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public Member save(final Member member) {
        return jpaMemberRepository.save(member);
    }
}
