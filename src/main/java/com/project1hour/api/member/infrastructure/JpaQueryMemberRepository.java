package com.project1hour.api.member.infrastructure;

import com.project1hour.api.member.domain.Member;
import com.project1hour.api.member.domain.QueryMemberRepository;
import org.springframework.data.repository.Repository;

public interface JpaQueryMemberRepository extends QueryMemberRepository, Repository<Member, Long> {
}
