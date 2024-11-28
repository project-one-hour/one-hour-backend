package com.project1hour.api.core.application.user.imports;

import com.project1hour.api.core.application.user.model.TokenPackage;
import com.project1hour.api.core.application.user.model.UserSocialInfo;
import com.project1hour.api.core.domain.user.entity.User;
import java.util.Optional;

public interface UserCommandPort {

    User saveUser(User user);

    User saveAuthenticatedUser(User user);

    User saveUserInterestsByUser(User user);

    User saveProfileImagesByUser(User user);

    Optional<User> saveUserOauthInfo(String provider, UserSocialInfo userSocialInfo, TokenPackage tokenPackage);
}
