package com.project1hour.api.global.config;

import com.project1hour.api.core.infrastructure.auth.kakao.KakaoApiFetcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Slf4j
@Configuration
public class HttpInterfaceConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        return restTemplate;
    }

    @Bean
    public KakaoApiFetcher KakaoApiFetcher() {
        return createApiClient(KakaoApiFetcher.class, restTemplate());
    }

    private <T> T createApiClient(Class<T> clientType, RestTemplate restTemplate) {
        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplate);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(clientType);
    }
}
