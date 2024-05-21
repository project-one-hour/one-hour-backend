package com.project1hour.api.core.implement.auth;

import com.project1hour.api.core.domain.auth.AuthProvider;
import com.project1hour.api.core.domain.auth.AuthProviderRepository;
import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.exception.auth.AuthProviderNotFoundException;
import com.project1hour.api.core.implement.auth.dto.SocialInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class AuthProcessor {

    private final AuthProviderRepository authProviderRepository;

    public void save(final Member savedMember, final SocialInfo socialInfo) {
        AuthProvider authProvider = AuthProvider.builder()
                .email(socialInfo.getEmail())
                .provider(socialInfo.getProvider())
                .providerId(socialInfo.getProviderId())
                .member(savedMember)
                .build();

        authProviderRepository.save(authProvider);
    }

    public void updateAuthProfile(final SocialInfo socialInfo) {
        AuthProvider authProvider = authProviderRepository.findByProviderId(socialInfo.getProviderId())
                .orElseThrow(AuthProviderNotFoundException::new);

        authProvider.updateEmail(socialInfo.getEmail());
    }
}
