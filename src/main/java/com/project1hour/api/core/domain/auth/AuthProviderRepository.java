package com.project1hour.api.core.domain.auth;

import java.util.Optional;

public interface AuthProviderRepository {

    AuthProvider save(AuthProvider authProvider);

    Optional<AuthProvider> findByProviderIdOrEmail(String providerId, String email);

    void updateEmailByProviderId(String providerId, String email);
}
