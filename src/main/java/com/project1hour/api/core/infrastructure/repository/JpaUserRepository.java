package com.project1hour.api.core.infrastructure.repository;

import com.project1hour.api.core.infrastructure.persistence.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByNickname(String nickname);
}
