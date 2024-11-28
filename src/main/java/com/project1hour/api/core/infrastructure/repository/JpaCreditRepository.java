package com.project1hour.api.core.infrastructure.repository;

import com.project1hour.api.core.infrastructure.persistence.CreditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCreditRepository extends JpaRepository<CreditEntity, Long> {
}
