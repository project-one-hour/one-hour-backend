package com.project1hour.api.global.support;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.project1hour.api.core.application.user.exports.TokenAuthenticationFacade;
import com.project1hour.api.core.application.user.model.UserDetail;
import com.project1hour.api.core.presentation.filter.AuthenticationFilter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

@TestConfiguration
public class RestDocsTestConfiguration {

    public static final Long MOCK_USER_ID = 1L;

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter(tokenAuthenticationFacade());
    }

    @Bean
    public TokenAuthenticationFacade tokenAuthenticationFacade() {
        TokenAuthenticationFacade facade = mock(TokenAuthenticationFacade.class);
        given(facade.authenticateUser(any())).willReturn(new UserDetail(MOCK_USER_ID));
        return facade;
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
