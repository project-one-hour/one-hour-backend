package com.project1hour.api.core.application.user.imports;


import com.project1hour.api.core.application.user.model.TokenPackage;
import com.project1hour.api.core.application.user.model.WebKey;
import java.util.List;

public interface OauthRestClient {

    String STATIC_GRANT_TYPE = "authorization_code";

    boolean isSupport(String providerName);

    TokenPackage requestTokenPackageByAuthorizationCode(String authorizationCode);

    List<WebKey> requestWebKeys();
}
