package com.project1hour.api.core.infrastructure.auth.kakao;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.net.URI;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;

public interface KakaoApiFetcher {

    @GetExchange
    KakaoSocialInfo fetchKakaoUserInfo(URI uri, @RequestHeader(value = AUTHORIZATION) String accessToken);
}
