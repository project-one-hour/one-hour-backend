package com.project1hour.api.core.domain.user;

import com.project1hour.api.core.domain.user.entity.User;
import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    // command
    User saveUser(User user);

    //query
    Optional<User> findUserById(Long userId);

    boolean existsByNickname(String nickname);

    boolean hasMissingInterestIds(Collection<Long> interestIds);
}
