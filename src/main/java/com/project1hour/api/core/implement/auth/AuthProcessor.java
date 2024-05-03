package com.project1hour.api.core.implement.auth;

import com.project1hour.api.core.domain.auth.AuthProvider;
import com.project1hour.api.core.domain.auth.AuthProviderRepository;
import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.implement.auth.dto.SocialInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthProcessor {

    private final AuthProviderRepository authProviderRepository;

    @Transactional
    public void save(final Member savedMember, final SocialInfo socialInfo) {
        AuthProvider authProvider = AuthProvider.builder()
                .email(socialInfo.getEmail())
                .provider(socialInfo.getProvider())
                .providerId(socialInfo.getProviderId())
                .member(savedMember)
                .build();

        authProviderRepository.save(authProvider);
    }
}
