package com.project1hour.api.global.support;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.snippet.Attributes.key;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1hour.api.core.presentation.filter.AuthenticationFilter;
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

@Disabled
@WebMvcTest
@Import(RestDocsTestConfiguration.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class RestDocsTestSupport {

    private static final String CONSTRAINTS = "constraints";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestDocumentationResultHandler restDocumentationResultHandler;

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @BeforeEach
    final void setUp(final WebApplicationContext context, final RestDocumentationContextProvider contextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(contextProvider))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocumentationResultHandler)
                .addFilter(authenticationFilter)
                .build();
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
