package com.project1hour.api.core.infrastructure.user;

import com.project1hour.api.core.domain.user.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuthRepository extends JpaRepository<Auth, Long> {
}
