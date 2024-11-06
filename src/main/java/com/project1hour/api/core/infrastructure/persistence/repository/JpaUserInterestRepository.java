package com.project1hour.api.core.infrastructure.persistence.repository;

import com.project1hour.api.core.domain.user.entity.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserInterestRepository extends JpaRepository<UserInterest, Long> {
}
