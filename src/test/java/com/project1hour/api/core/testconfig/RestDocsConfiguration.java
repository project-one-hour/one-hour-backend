package com.project1hour.api.core.testconfig;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.project1hour.api.core.presentation.filter.AuthenticationFilter;
import com.project1hour.api.core.presentation.filter.ImageOptimizationFilter;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

@TestConfiguration
public class RestDocsConfiguration {

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return Mockito.mock(AuthenticationFilter.class);
    }

    @Bean
    public ImageOptimizationFilter imageOptimizationFilter() {
        return Mockito.mock(ImageOptimizationFilter.class);
    }

    @Bean
    public RestDocumentationResultHandler restDocumentationResultHandler() {
        return MockMvcRestDocumentation.document(
                "{class-name}/{method-name}",  // 문서 이름 설정
                preprocessRequest(  // 공통 헤더 설정
                        modifyHeaders()
                                .remove("Content-Length")
                                .remove("Host"),
                        prettyPrint()),  // pretty json 적용
                preprocessResponse(  // 공통 헤더 설정
                        modifyHeaders()
                                .remove("Content-Length")
                                .remove("X-Content-Type-Options")
                                .remove("X-XSS-Protection")
                                .remove("Cache-Control")
                                .remove("Pragma")
                                .remove("Expires")
                                .remove("X-Frame-Options"),
                        prettyPrint())    // pretty json 적용
        );
    }
}
