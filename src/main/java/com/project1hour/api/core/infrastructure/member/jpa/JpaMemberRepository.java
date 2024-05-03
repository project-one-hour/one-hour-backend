package com.project1hour.api.core.infrastructure.member.jpa;

import com.project1hour.api.core.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {
}
