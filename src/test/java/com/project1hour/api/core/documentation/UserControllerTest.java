package com.project1hour.api.core.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project1hour.api.core.application.user.exports.CheckNicknameDuplicationService;
import com.project1hour.api.core.application.user.exports.OauthLoginService;
import com.project1hour.api.core.application.user.exports.UserRegistrationService;
import com.project1hour.api.core.presentation.controller.UserController;
import com.project1hour.api.global.support.RestDocsTestSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;

@WebMvcTest(UserController.class)
public class UserControllerTest extends RestDocsTestSupport {

    @MockBean
    private CheckNicknameDuplicationService checkNicknameDuplicationService;

    @MockBean
    private UserRegistrationService userRegistrationService;

    @MockBean
    private OauthLoginService oauthLoginService;

    @Test
    @DisplayName("닉네임이 중복되지 않으면 false를 반환한다")
    void isDuplicated_200() throws Exception {
        var response = new CheckNicknameDuplicationService.Response(false);
        given(checkNicknameDuplicationService.checkNickNameDuplication(any())).willReturn(response);

        mockMvc().perform(get("/api/users/duplicate/{nickname}", "아무개"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isDuplicate").value(false))
                .andDo(restDocs().document(
                        pathParameters(
                                parameterWithName("nickname")
                                        .description("중복 검사할 닉네임")
                        ),
                        responseFields(
                                fieldWithPath("isDuplicate")
                                        .type(JsonFieldType.BOOLEAN)
                                        .description("중복 여부")
                        )
                ));
    }

    @Test
    @DisplayName("회원가입")
    void signup_201() throws Exception {
        doNothing().when(userRegistrationService).signUpUser(any());

        MockMultipartFile primaryImage = new MockMultipartFile(
                "primaryImage",
                "MockImage1.jpeg",
                "image/jpeg",
                "Hello World~".getBytes()
        );
        MockMultipartFile secondaryImages1 = new MockMultipartFile(
                "secondaryImages",
                "MockImage2.jpeg",
                "image/jpeg",
                "Hello World~~".getBytes()
        );
        MockMultipartFile secondaryImages2 = new MockMultipartFile(
                "secondaryImages",
                "MockImage3.jpeg",
                "image/jpeg",
                "Hello World~~~".getBytes()
        );

        Map<String, Object> profile = HashMap.newHashMap(7);
        profile.put("nickname", "아무개");
        profile.put("gender", "male");
        profile.put("birthday", "1990-01-01");
        profile.put("mbti", "intj");
        profile.put("interestIds", List.of(1, 2, 3, 4, 5));
        profile.put("isMarketingAllowed", true);
        profile.put("isNotificationAllowed", true);

        mockMvc().perform(multipart(HttpMethod.POST, "/api/users/signup")
                        .file(primaryImage)
                        .file(secondaryImages1)
                        .file(secondaryImages2)
                        .param("profile", createJson(profile))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.token.here"))
                .andExpect(status().isCreated())
                .andDo(restDocs().document(
                        requestFields(
                                fieldWithPath("nickname")
                                        .type(JsonFieldType.STRING)
                                        .description("사용자 닉네임"),
                                fieldWithPath("gender")
                                        .type(JsonFieldType.STRING)
                                        .description("성별")
                                        .attributes(constraints("MALE or FEMALE만 입력 가능 (대소문자 상관 없음)")),
                                fieldWithPath("birthday")
                                        .type(JsonFieldType.STRING)
                                        .description("생년월일")
                                        .attributes(constraints("yyyy-DD-mm 포맷 입력")),
                                fieldWithPath("mbti")
                                        .type(JsonFieldType.STRING)
                                        .description("MBTI")
                                        .attributes(constraints("존재하는 MBTI만 입력 가능 (대소문자 상관 없음)")),
                                fieldWithPath("interests")
                                        .type(JsonFieldType.ARRAY)
                                        .description("관심사 목록")
                                        .attributes(constraints("DB에 존재하는 관심사만 가능, 반드시 5개여야 함")),
                                fieldWithPath("isMarketingAllowed")
                                        .type(JsonFieldType.BOOLEAN)
                                        .description("마케팅 정보 수신 동의 여부")
                                        .attributes(constraints("true 수신 동의, false 수신 거부")),
                                fieldWithPath("isNotificationAllowed")
                                        .type(JsonFieldType.BOOLEAN)
                                        .description("알림 권한 동의 여부")
                                        .attributes(constraints("true 권한 동의, false 권한 거부"))
                        ),
                        requestParts(
                                partWithName("primaryImage")
                                        .description("업로드할 대표 프로필 사진"),
                                partWithName("secondaryImages")
                                        .optional()
                                        .description("업로드할 서브 프로필 사진들")
                        )
                ));
    }

    @Test
    @DisplayName("")
    void login_200() throws Exception {
        var response = new OauthLoginService.Response(
                "jwt.access.token",
                "jwt.refresh.token",
                true
        );
        given(oauthLoginService.login(any())).willReturn(response);

        mockMvc().perform(get("/api/users/auth-callback/{provider}", "kakao", "apple")
                        .param("code", "AuthorizationCode"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(response.accessToken()))
                .andExpect(jsonPath("$.refreshToken").value(response.refreshToken()))
                .andDo(restDocs().document(
                        pathParameters(
                                parameterWithName("provider")
                                        .description("OAuth 제공자 (예: kakao, apple 등)")
                        ),
                        queryParameters(
                                parameterWithName("code")
                                        .description("OAuth 제공자로부터 받은 인증 코드")
                        ),
                        responseFields(
                                fieldWithPath("accessToken")
                                        .type(JsonFieldType.STRING)
                                        .description("JWT AccessToken"),
                                fieldWithPath("refreshToken")
                                        .type(JsonFieldType.STRING)
                                        .description("JWT RefreshToken"),
                                fieldWithPath("accessToken")
                                        .type(JsonFieldType.BOOLEAN)
                                        .description("신규 가입 여부")
                        )
                ));
    }
}
