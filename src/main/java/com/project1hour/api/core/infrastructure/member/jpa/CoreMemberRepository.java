package com.project1hour.api.core.infrastructure.member.jpa;

import static com.project1hour.api.global.advice.ErrorCode.MEMBER_NOT_FOUND;

import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.domain.member.MemberRepository;
import com.project1hour.api.global.advice.NotFoundException;
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

    @Override
    public Member getById(final Long id) {
        return jpaMemberRepository.findById(id)
                .orElseThrow(() -> {
                    String message = String.format("존재하지 않는 사용자 입니다. id = %d", id);
                    return new NotFoundException(message, MEMBER_NOT_FOUND);
                });
    }

    @Override
    public boolean isExistByNickname(final String nickname) {
        return jpaMemberRepository.existsByNicknameValue(nickname);
    }
}
