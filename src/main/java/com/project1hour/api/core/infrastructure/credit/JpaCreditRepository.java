package com.project1hour.api.core.infrastructure.credit;

import com.project1hour.api.core.domain.credit.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCreditRepository extends JpaRepository<Credit, Long> {
}
