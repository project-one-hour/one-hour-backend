package com.project1hour.api.core.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import com.project1hour.api.core.implement.auth.dto.TokenResponse;
import com.project1hour.api.core.presentation.auth.dto.OauthAccessTokenRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class AuthDocument extends DocumentationTest {

    @Nested
    class 소셜로그인을_요청한다 {

        @Test
        void 로그인에_성공하면_회원_토큰을_반환한다() {
            OauthAccessTokenRequest tokenRequest = new OauthAccessTokenRequest("oauth-accessToken-or-idToken");
            TokenResponse tokenResponse = new TokenResponse("jwt.token.response", true);
            given(authService.createToken(any(), any())).willReturn(tokenResponse);

            docsGiven.contentType(MediaType.APPLICATION_JSON_VALUE)
                    .pathParam("provider", "kakao")
                    .body(tokenRequest)
                    .when().post("/api/auth/{provider}")
                    .then().log().all()
                    .apply(document("member/oauth-login/success",

                            pathParameters(
                                    parameterWithName("provider").description("kakao, apple과 같은 social provider")
                            ),
                            requestFields(
                                    fieldWithPath("token").type(JsonFieldType.STRING)
                                            .description("Social Provider AccessToken (or IdToken)")),
                            responseFields(
                                    fieldWithPath("accessToken").type(JsonFieldType.STRING)
                                            .description("JWT AccessToken"),
                                    fieldWithPath("isNew").type(JsonFieldType.BOOLEAN).description("신규 가입 여부")
                            )
                    ))
                    .statusCode(HttpStatus.OK.value());
        }
    }
}
