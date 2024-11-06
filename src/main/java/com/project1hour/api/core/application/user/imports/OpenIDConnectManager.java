package com.project1hour.api.core.application.user.imports;

import com.project1hour.api.core.application.user.model.UserSocialInfo;
import com.project1hour.api.core.application.user.model.WebKey;
import java.util.List;

public interface OpenIDConnectManager {

    boolean isSupport(String providerName);

    UserSocialInfo parseIdTokenByWebKeys(String idToken, List<WebKey> webKeys);
}
