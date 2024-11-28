package com.project1hour.api.core.application.user.imports;

import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.core.domain.user.value.InterestId;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.core.domain.user.value.UserId;
import java.util.List;
import java.util.Optional;

public interface UserQueryPort {

    boolean existsByUserNickname(Nickname userNickname);

    boolean existsAllByInterestIdIn(List<InterestId> interestIds);

    Optional<User> findAuthenticatedUserById(UserId userId);
}
