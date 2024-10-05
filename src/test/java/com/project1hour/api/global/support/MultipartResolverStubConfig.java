package com.project1hour.api.global.support;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.multipart.MultipartResolver;

@Configuration
public class MultipartResolverStubConfig {

    @Bean
    @Profile("test")
    @ConditionalOnMissingBean
    public MultipartResolver multipartResolver() {
        return Mockito.mock(MultipartResolver.class);
    }
}
