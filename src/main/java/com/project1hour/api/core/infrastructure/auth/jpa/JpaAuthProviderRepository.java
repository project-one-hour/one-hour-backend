package com.project1hour.api.core.infrastructure.auth.jpa;

import com.project1hour.api.core.domain.auth.AuthProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuthProviderRepository extends JpaRepository<AuthProvider, Long> {

    Optional<AuthProvider> findByProviderId(String providerId);

    Optional<AuthProvider> findByEmail(String email);
}
