package com.project1hour.api.core.infrastructure.persistence.repository;

import com.project1hour.api.core.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    boolean existsByNicknameValue(String nickname);
}
