package com.project1hour.api.core.domain.auth;

import java.util.Optional;

public interface AuthProviderRepository {

    AuthProvider save(AuthProvider authProvider);

    Optional<AuthProvider> findByProviderId(String providerId);

    Optional<AuthProvider> findByEmail(String email);
}
