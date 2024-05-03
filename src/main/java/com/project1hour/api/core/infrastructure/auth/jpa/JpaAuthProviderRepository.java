package com.project1hour.api.core.infrastructure.auth.jpa;

import com.project1hour.api.core.domain.auth.AuthProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuthProviderRepository extends JpaRepository<AuthProvider, Long> {

    boolean existsByProviderId(String providerId);

    Optional<AuthProvider> findByProviderId(String providerId);
}
