package com.project1hour.api.core.infrastructure.user;

import com.project1hour.api.core.domain.user.User;
import com.project1hour.api.core.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserProcessRepository implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(final User user) {
        return jpaUserRepository.save(user);
    }
}
