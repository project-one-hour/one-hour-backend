package com.project1hour.api.core.infrastructure.persistence.repository;

import com.project1hour.api.core.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    boolean existsByNicknameValue(String nickname);

    Optional<User> findByUserAuthId(Long userAuthId);
}
