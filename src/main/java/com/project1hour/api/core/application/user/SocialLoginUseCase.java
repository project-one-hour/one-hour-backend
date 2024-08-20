package com.project1hour.api.core.application.user;

import com.project1hour.api.core.application.user.api.OauthClientFactory;
import com.project1hour.api.core.application.user.api.SocialProfileId;
import com.project1hour.api.core.application.user.service.SocialLoginService;
import com.project1hour.api.core.domain.user.UserRepository;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SocialLoginUseCase implements SocialLoginService {

    private final OauthClientFactory oauthClientFactory;
    private final UserRepository userRepository;

    @Override
    public Long login(SocialLoginService.Request request) {
        User authentiactedUser = authenticateUserWithSocialProfile(request.provider(), request.accessToken());
        return authentiactedUser.getId();
    }

    /**
     * Command : 소셜 로그인
     */
    protected User authenticateUserWithSocialProfile(final String provider, final String accessToken) {
        SocialProfileId socialProfileId =
                oauthClientFactory.getOauthClientByProvider(provider).findSocialProfileIdByAccessToken(accessToken);

        User user = userRepository.findByAuthSocialProfileId(socialProfileId.id())
                .orElseThrow(() -> new NotFoundException("해당하는 사용자를 찾을 수 없습니다.", ErrorCode.AUTH_PROVIDER_NOT_FOUND));

        return user;
    }
}
