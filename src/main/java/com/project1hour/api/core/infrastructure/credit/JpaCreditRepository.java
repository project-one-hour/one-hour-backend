package com.project1hour.api.core.infrastructure.credit;

import com.project1hour.api.core.domain.credit.Credit;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCreditRepository extends JpaRepository<Credit, Long> {

    Optional<Credit> findByMemberId(Long memberId);
}
