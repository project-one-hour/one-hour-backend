package com.project1hour.api.core.infrastructure.user;

import com.project1hour.api.core.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    boolean existsByNickNameValue(String nickname);
}
