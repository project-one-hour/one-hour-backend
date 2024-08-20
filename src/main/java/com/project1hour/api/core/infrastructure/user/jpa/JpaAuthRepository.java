package com.project1hour.api.core.infrastructure.user.jpa;

import com.project1hour.api.core.domain.user.entity.Auth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuthRepository extends JpaRepository<Auth, Long> {

    boolean existsByInfoSocialProfileId(String socialProfileId);

    Optional<Auth> findByInfoSocialProfileId(String socialProfileId);
}
