package com.project1hour.api.core.domain.user;

import com.project1hour.api.core.domain.user.entity.User;
import java.util.Optional;

public interface UserRepository {

    // command
    User save(User user);

    //query
    boolean existsByNickname(String nickname);

    boolean existsAuthBySocialProfileId(String socialProfileId);

    Optional<User> findByAuthSocialProfileId(String socialProfileId);
}
