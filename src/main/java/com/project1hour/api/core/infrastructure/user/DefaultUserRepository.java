package com.project1hour.api.core.infrastructure.user;

import com.project1hour.api.core.domain.user.UserRepository;
import com.project1hour.api.core.domain.user.entity.Auth;
import com.project1hour.api.core.domain.user.entity.Interest;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.infrastructure.user.jpa.JpaAuthRepository;
import com.project1hour.api.core.infrastructure.user.jpa.JpaUserRepository;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DefaultUserRepository implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final JpaAuthRepository jpaAuthRepository;
    private final Map<Long, Interest> interestRepository = Interest.INTEREST_IDS;

    @Override
    public User save(final User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return jpaUserRepository.existsByNicknameValue(nickname);
    }

    @Override
    public boolean existsAuthBySocialProfileId(final String socialProfileId) {
        return jpaAuthRepository.existsByAuthInfoSocialProfileId(socialProfileId);
    }

    @Override
    public Optional<User> findByAuthSocialProfileId(final String socialProfileId) {
        return jpaAuthRepository.findByAuthInfoSocialProfileId(socialProfileId)
                .map(Auth::getUser);
    }

    @Override
    public boolean hasMissingInterestIds(Collection<Long> interestIds) {
        return interestIds.stream().anyMatch(interestId -> !interestRepository.containsKey(interestId));
    }
}
