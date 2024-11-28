package com.project1hour.api.core.infrastructure.repository;

import com.project1hour.api.core.infrastructure.persistence.UserInterestEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserInterestRepository extends JpaRepository<UserInterestEntity, Long> {

    List<UserInterestEntity> findByUserId(Long userId);
}
