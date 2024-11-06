package com.project1hour.api.core.infrastructure.persistence.repository;

import com.project1hour.api.core.application.user.imports.UserApplicationRepository;
import com.project1hour.api.core.application.user.model.TokenPackage;
import com.project1hour.api.core.domain.user.entity.Interest;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.infrastructure.persistence.entity.OauthInfoEntity;
import com.project1hour.api.core.infrastructure.persistence.entity.ProviderType;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DefaultUserRepository implements UserApplicationRepository {

    private final JpaUserRepository jpaUserRepository;
    private final JpaOauthInfoRepository jpaOauthInfoRepository;
    private final Map<Long, Interest> interestRepository = Interest.INTEREST_IDS;

    @Override
    public User saveUser(final User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(final Long userId) {
        return jpaUserRepository.findById(userId);
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return jpaUserRepository.existsByNicknameValue(nickname);
    }

    @Override
    public boolean hasMissingInterestIds(final Collection<Long> interestIds) {
        return interestIds.stream().anyMatch(interestId -> !interestRepository.containsKey(interestId));
    }

    @Override
    public User saveUserOauthInfo(final String provider, final String userSocialId,
                                  final TokenPackage tokenPackage) {
        ProviderType providerType = ProviderType.find(provider);
        OauthInfoEntity oauthInfo = jpaOauthInfoRepository
                .findByProviderTypeAndUserSocialId(providerType, userSocialId)
                .map(OauthInfoEntity::toBuilder)
                .orElseGet(() -> {
                    User pendingUser = jpaUserRepository.save(User.createPendingUser());
                    return OauthInfoEntity.builder()
                            .userSocialId(userSocialId)
                            .providerType(providerType)
                            .user(pendingUser);
                })
                .accessToken(tokenPackage.accessToken())
                .refreshToken(tokenPackage.refreshToken())
                .accessTokenExpiresIn(tokenPackage.accessTokenExpiresIn())
                .refreshTokenExpiresIn(tokenPackage.refreshTokenExpiresIn())
                .build();

        OauthInfoEntity savedOauthInfo = jpaOauthInfoRepository.save(oauthInfo);
        return savedOauthInfo.getUser();
    }
}
