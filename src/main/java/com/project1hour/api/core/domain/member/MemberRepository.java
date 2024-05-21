package com.project1hour.api.core.domain.member;

public interface MemberRepository {

    Member save(Member member);

    Member getById(Long id);

    boolean isExistByNickname(String nickname);
}
