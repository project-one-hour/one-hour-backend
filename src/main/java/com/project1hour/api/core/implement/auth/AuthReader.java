package com.project1hour.api.core.implement.auth;

import com.project1hour.api.core.domain.auth.AuthProvider;
import com.project1hour.api.core.domain.auth.AuthProviderRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthReader {

    private final AuthProviderRepository authProviderRepository;

    @Transactional
    public Optional<Long> findExistsMemberId(final String providerId) {
        Optional<AuthProvider> optionalAuthProvider = authProviderRepository.findByProviderId(providerId);

        return optionalAuthProvider.map(AuthProvider::getId);
    }

}
