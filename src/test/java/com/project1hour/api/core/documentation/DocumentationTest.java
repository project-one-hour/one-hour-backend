package com.project1hour.api.core.documentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.project1hour.api.core.application.auth.AuthService;
import com.project1hour.api.core.application.bungae.BungaeService;
import com.project1hour.api.core.application.credit.CreditService;
import com.project1hour.api.core.application.image.manager.ImageDetailManager;
import com.project1hour.api.core.application.image.manager.ImageExpressionManager;
import com.project1hour.api.core.application.image.manager.ImageValidationManager;
import com.project1hour.api.core.application.member.MemberService;
import com.project1hour.api.core.application.user.manager.TokenAuthManager;
import com.project1hour.api.core.domain.auth.AuthenticationContext;
import com.project1hour.api.core.domain.auth.TokenProvider;
import com.project1hour.api.core.domain.image.ImageUploader;
import com.project1hour.api.core.presentation.auth.AuthController;
import com.project1hour.api.core.presentation.bungae.BungaeController;
import com.project1hour.api.core.presentation.credit.CreditController;
import com.project1hour.api.core.presentation.image.ImageController;
import com.project1hour.api.core.presentation.interest.InterestController;
import com.project1hour.api.core.presentation.member.MemberController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartResolver;

@WebMvcTest({
        AuthController.class,
        MemberController.class,
        InterestController.class,
        ImageController.class,
        CreditController.class,
        BungaeController.class
})
@ExtendWith(RestDocumentationExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class DocumentationTest {

    protected MockMvcRequestSpecification docsGiven;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected CreditService creditService;

    @MockBean
    protected BungaeService bungaeService;

    @MockBean
    protected ImageUploader imageUploader;

    @MockBean
    protected TokenProvider tokenProvider;

    @MockBean
    protected AuthenticationContext authenticationContext;

    @MockBean
    protected TokenAuthManager tokenAuthManager;

    @MockBean
    protected MultipartResolver multipartResolver;

    @MockBean
    protected ImageExpressionManager imageExpressionManager;

    @MockBean
    protected ImageValidationManager imageValidationManager;

    @MockBean
    protected ImageDetailManager imageDetailManager;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext,
               final RestDocumentationContextProvider restDocumentation) {
        docsGiven = RestAssuredMockMvc.given()
                .mockMvc(MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        .apply(documentationConfiguration(restDocumentation)
                                .operationPreprocessors()
                                .withRequestDefaults(prettyPrint())
                                .withResponseDefaults(prettyPrint()))
                        .build()).log().all();
    }
}
