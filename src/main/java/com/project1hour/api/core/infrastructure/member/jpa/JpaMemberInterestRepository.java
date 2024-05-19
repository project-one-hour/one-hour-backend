package com.project1hour.api.core.infrastructure.member.jpa;

import com.project1hour.api.core.domain.member.MemberInterest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberInterestRepository extends JpaRepository<MemberInterest, Long> {
}
