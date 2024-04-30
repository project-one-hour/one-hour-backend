package com.project1hour.api.auth.infrastructure.kakao;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.project1hour.api.auth.application.dto.KakaoSocialInfoResponse;
import java.net.URI;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;

public interface KakaoApiFetcher {

    @GetExchange
    KakaoSocialInfoResponse fetchKakaoUserInfo(URI uri, @RequestHeader(value = AUTHORIZATION) String accessToken);
}
