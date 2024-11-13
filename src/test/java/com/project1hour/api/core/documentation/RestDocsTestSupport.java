package com.project1hour.api.core.documentation;

import static com.project1hour.api.core.presentation.filter.AuthenticationFilter.AUTHENTICATED_USER;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.snippet.Attributes.key;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1hour.api.core.application.user.model.UserDetail;
import com.project1hour.api.core.testconfig.RestDocsConfiguration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@Disabled
@WebMvcTest
@Import(RestDocsConfiguration.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class RestDocsTestSupport {

    private static final String CONSTRAINTS = "constraints";
    protected static final Long MOCK_USER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestDocumentationResultHandler restDocumentationResultHandler;

    @BeforeEach
    final void setUp(final WebApplicationContext context, final RestDocumentationContextProvider contextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(contextProvider))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocumentationResultHandler)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .addFilter(this::stubAuthenticateFilter)
                .build();
    }

    private void stubAuthenticateFilter(final ServletRequest request, final ServletResponse response,
                                        final FilterChain filterChain) throws IOException, ServletException {
        request.setAttribute(AUTHENTICATED_USER, new UserDetail(MOCK_USER_ID));
        filterChain.doFilter(request, response);
    }

    protected final String createJson(final Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    protected final Attributes.Attribute constraints(final String value) {
        return key(CONSTRAINTS).value(value);
    }

    protected final MockMvc mockMvc() {
        return this.mockMvc;
    }

    protected final RestDocumentationResultHandler restDocs() {
        return this.restDocumentationResultHandler;
    }
}
