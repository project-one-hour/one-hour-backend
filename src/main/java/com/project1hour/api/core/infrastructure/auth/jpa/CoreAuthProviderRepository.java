package com.project1hour.api.core.infrastructure.auth.jpa;

import com.project1hour.api.core.domain.auth.AuthProvider;
import com.project1hour.api.core.domain.auth.AuthProviderRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CoreAuthProviderRepository implements AuthProviderRepository {

    private final JpaAuthProviderRepository jpaAuthProviderRepository;

    @Override
    public AuthProvider save(final AuthProvider authProvider) {
        return jpaAuthProviderRepository.save(authProvider);
    }

    @Override
    public boolean existsByProviderId(final String providerId) {
        return jpaAuthProviderRepository.existsByProviderId(providerId);
    }

    @Override
    public Optional<AuthProvider> findByProviderId(final String providerId) {
        return jpaAuthProviderRepository.findByProviderId(providerId);
    }
}
