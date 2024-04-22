package com.project1hour.api.member.infrastructure;

import com.project1hour.api.member.domain.CommandMemberRepository;
import com.project1hour.api.member.domain.Member;
import org.springframework.data.repository.Repository;

public interface JpaCommandMemberRepository extends CommandMemberRepository, Repository<Member, Long> {
}
