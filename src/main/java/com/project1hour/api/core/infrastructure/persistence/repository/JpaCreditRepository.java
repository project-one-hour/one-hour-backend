package com.project1hour.api.core.infrastructure.persistence.repository;

import com.project1hour.api.core.domain.credit.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCreditRepository extends JpaRepository<Credit, Long> {
}
