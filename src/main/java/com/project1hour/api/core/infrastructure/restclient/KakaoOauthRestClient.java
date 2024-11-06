package com.project1hour.api.core.infrastructure.restclient;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1hour.api.core.application.user.imports.OauthRestClient;
import com.project1hour.api.core.application.user.model.TokenPackage;
import com.project1hour.api.core.application.user.model.WebKey;
import com.project1hour.api.core.infrastructure.restclient.model.KakaoRequestTokensBody;
import com.project1hour.api.core.infrastructure.restclient.model.KakaoResponseJWKsBody;
import com.project1hour.api.core.infrastructure.restclient.model.KakaoResponseTokensBody;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class KakaoOauthRestClient implements OauthRestClient {

    public static final String PROVIDER_TYPE = "KAKAO";

    private final String requestTokensUrl;
    private final String requestPublicKeyUrl;
    private final String kakaoApiKey;
    private final String redirectUrl;

    private final RestClient restClient;

    public KakaoOauthRestClient(@Value("${oauth2.kakao.url.tokens}") final String requestTokensUrl,
                                @Value("${oauth2.kakao.url.public-key}") final String requestPublicKeyUrl,
                                @Value("${oauth2.kakao.url.callback}") final String redirectUrl,
                                @Value("${oauth2.kakao.api-key}") final String kakaoApiKey,
                                final RestClient.Builder restClientBuilder) {
        this.requestTokensUrl = requestTokensUrl;
        this.requestPublicKeyUrl = requestPublicKeyUrl;
        this.kakaoApiKey = kakaoApiKey;
        this.redirectUrl = redirectUrl;
        this.restClient = restClientBuilder.build();
    }

    @Override
    public boolean isSupport(final String providerName) {
        return StringUtils.equalsIgnoreCase(providerName, PROVIDER_TYPE);
    }

    @Override
    public TokenPackage requestTokenPackageByAuthorizationCode(final String authorizationCode) {
        return restClient.post()
                .uri(requestTokensUrl)
                .contentType(APPLICATION_FORM_URLENCODED)
                .body(new KakaoRequestTokensBody(kakaoApiKey, redirectUrl, authorizationCode).toMultiValueMap())
                .retrieve()
                .body(KakaoResponseTokensBody.class);
    }

    @Override
    public List<WebKey> requestWebKeys() {
        KakaoResponseJWKsBody response = restClient.get()
                .uri(requestPublicKeyUrl)
                .retrieve()
                .body(KakaoResponseJWKsBody.class);

        try {
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return Collections.unmodifiableList(response.keys());
    }
}
