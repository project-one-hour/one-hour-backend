package com.project1hour.api.core.application.user.imports;

import com.project1hour.api.core.application.user.model.TokenPackage;
import com.project1hour.api.core.domain.user.UserRepository;
import com.project1hour.api.core.domain.user.entity.User;

public interface UserApplicationRepository extends UserRepository {

    User saveUserOauthInfo(String provider, String userSocialId, TokenPackage tokenPackage);

}
