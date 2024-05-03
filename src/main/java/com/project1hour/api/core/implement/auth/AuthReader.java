package com.project1hour.api.core.implement.auth;

import com.project1hour.api.core.domain.auth.AuthProvider;
import com.project1hour.api.core.domain.auth.AuthProviderRepository;
import com.project1hour.api.core.implement.auth.dto.SocialInfo;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthReader {

    private final AuthProviderRepository authProviderRepository;

    @Transactional
    public Optional<Long> readExistsMemberId(final SocialInfo socialInfo) {
        Optional<AuthProvider> existsAuthProvider = authProviderRepository.findByProviderId(
                socialInfo.getProviderId());

        if (existsAuthProvider.isEmpty()) {
            existsAuthProvider = authProviderRepository.findByEmail(socialInfo.getEmail());
        }

        return existsAuthProvider.map(authProvider -> authProvider.getMember().getId());
    }
}
