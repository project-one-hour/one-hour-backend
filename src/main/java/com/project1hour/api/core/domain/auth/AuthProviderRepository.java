package com.project1hour.api.core.domain.auth;

import java.util.Optional;

public interface AuthProviderRepository {

    AuthProvider save(AuthProvider authProvider);

    boolean existsByProviderId(String providerId);

    Optional<AuthProvider> findByProviderId(String providerId);
}
