package com.project1hour.api.core.infrastructure.persistence.repository;

import com.project1hour.api.core.domain.user.entity.Auth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuthRepository extends JpaRepository<Auth, Long> {

    boolean existsByAuthInfoSocialProfileId(String socialProfileId);

    Optional<Auth> findByAuthInfoSocialProfileId(String socialProfileId);
}
