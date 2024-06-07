package com.project1hour.api.core.implement.auth;

import com.project1hour.api.core.domain.auth.AuthProvider;
import com.project1hour.api.core.domain.auth.AuthProviderRepository;
import com.project1hour.api.core.domain.auth.SocialInfo;
import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthReader {

    private final AuthProviderRepository authProviderRepository;

    public Optional<Member> readExistsMember(final SocialInfo socialInfo) {
        AuthProvider authProvider = authProviderRepository.findByProviderIdOrEmail(socialInfo.getProviderId(),
                socialInfo.getEmail()).orElse(null);

        validateOtherSocialExists(socialInfo, authProvider);
        return Optional.ofNullable(authProvider)
                .map(AuthProvider::getMember);
    }

    private void validateOtherSocialExists(final SocialInfo socialInfo, final AuthProvider authProvider) {
        if (Objects.nonNull(authProvider) && isNotSameProvider(authProvider, socialInfo)) {
            String message = String.format("이미 %s로 소셜로그인을 한 내역이 있습니다. current = %s", authProvider.getProvider(),
                    socialInfo.getProvider());
            throw new BadRequestException(message, ErrorCode.OTHER_AUTH_AUTHENTICATED);
        }
    }

    private boolean isNotSameProvider(final AuthProvider authProvider, final SocialInfo socialInfo) {
        return authProvider.getProvider() != socialInfo.getProvider();
    }
}
