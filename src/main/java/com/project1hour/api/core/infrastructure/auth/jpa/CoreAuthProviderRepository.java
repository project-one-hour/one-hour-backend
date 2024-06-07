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
    public Optional<AuthProvider> findByProviderIdOrEmail(final String providerId, final String email) {
        return jpaAuthProviderRepository.findByProviderIdOrEmail(providerId, email);
    }

    @Override
    public void updateEmailByProviderId(final String providerId, final String email) {
        jpaAuthProviderRepository.updateEmailByProviderId(providerId, email);
    }
}
