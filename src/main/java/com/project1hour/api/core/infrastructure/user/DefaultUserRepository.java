package com.project1hour.api.core.infrastructure.user;

import com.project1hour.api.core.domain.user.UserRepository;
import com.project1hour.api.core.domain.user.entity.Auth;
import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.infrastructure.user.jpa.JpaAuthRepository;
import com.project1hour.api.core.infrastructure.user.jpa.JpaUserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DefaultUserRepository implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final JpaAuthRepository jpaAuthRepository;

    @Override
    public User save(final User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return jpaUserRepository.existsByNickNameValue(nickname);
    }

    @Override
    public boolean existsAuthBySocialProfileId(final String socialProfileId) {
        return jpaAuthRepository.existsByInfoSocialProfileId(socialProfileId);
    }

    @Override
    public Optional<User> findByAuthSocialProfileId(final String socialProfileId) {
        return jpaAuthRepository.findByInfoSocialProfileId(socialProfileId)
                .map(Auth::getUser);
    }
}
