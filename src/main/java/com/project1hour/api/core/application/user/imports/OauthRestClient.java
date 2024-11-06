package com.project1hour.api.core.application.user.imports;


import com.project1hour.api.core.application.user.model.TokenPackage;
import com.project1hour.api.core.application.user.model.WebKey;
import java.util.List;

public interface OauthRestClient {

    boolean isSupport(String providerName);

    TokenPackage requestTokenPackageByAuthorizationCode(String authorizationCode);

    List<WebKey> requestWebKeys();
}
