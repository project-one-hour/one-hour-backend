package com.project1hour.api.core.implement.auth;

import com.project1hour.api.core.domain.auth.AuthProvider;
import com.project1hour.api.core.domain.auth.AuthProviderRepository;
import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.implement.auth.dto.SocialInfo;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.NotFoundException;
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
                .orElseThrow(() -> new NotFoundException("Auth 정보를 찾을 수 없습니다.", ErrorCode.AUTH_PROVIDER_NOT_FOUND));

        authProvider.updateEmail(socialInfo.getEmail());
    }
}
