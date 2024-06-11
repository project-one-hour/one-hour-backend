package com.project1hour.api.core.infrastructure.auth.jpa;

import com.project1hour.api.core.domain.auth.AuthProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaAuthProviderRepository extends JpaRepository<AuthProvider, Long> {

    Optional<AuthProvider> findByProviderIdOrEmail(String providerId, String email);

    @Query("UPDATE AuthProvider a SET a.email = :email WHERE a.providerId = :providerId ")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void updateEmailByProviderId(String providerId, String email);
}
