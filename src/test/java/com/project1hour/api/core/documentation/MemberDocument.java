package com.project1hour.api.core.documentation;

import static com.project1hour.api.FakeImageGenerator.createFakeImage;
import static com.project1hour.api.core.documentation.DocumentFormatGenerator.getConstraints;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;

import com.project1hour.api.core.domain.member.Authority;
import com.project1hour.api.core.presentation.member.dto.SignUpRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class MemberDocument extends DocumentationTest {

    @Nested
    class 사용자_회원가입 {

        @Test
        void 회원가입을_성공하면_200을_반환한다() {
            doNothing().when(memberService).signUp(any(), any());
            given(tokenProvider.extractSubject("jwt.token.here")).willReturn("1");
            given(tokenProvider.extractAuthority("jwt.token.here")).willReturn(Authority.MEMBER);
            given(authenticatoinContext.getPrincipal()).willReturn("1");

            SignUpRequest signUpRequest = new SignUpRequest(
                    "닉네임",
                    "MALE",
                    LocalDate.of(1998, 10, 8),
                    "ENTJ",
                    List.of("반드시", "5개를", "입력해", "주어야", "한다"),
                    true
            );

            docsGiven.contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.token.here")
                    .body(signUpRequest)
                    .when().post("/api/members/signup")
                    .then().log().all()
                    .apply(document("member/signup/success",
                            requestFields(
                                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("사용자 닉네임"),
                                    fieldWithPath("gender").type(JsonFieldType.STRING).description("성별")
                                            .attributes(getConstraints("constraints",
                                                    "MALE or FEMALE만 입력 가능 (대소문자 상관 없음)")),
                                    fieldWithPath("birthday").type(JsonFieldType.STRING).description("생년월일")
                                            .attributes(getConstraints("constraints", "yyyy-DD-mm 포맷 입력")),
                                    fieldWithPath("mbti").type(JsonFieldType.STRING).description("MBTI")
                                            .attributes(getConstraints("constraints", "존재하는 MBTI만 입력 가능 (대소문자 상관 없음)")),
                                    fieldWithPath("interests").type(JsonFieldType.ARRAY).description("관심사 목록")
                                            .attributes(getConstraints("constraints", "DB에 존재하는 관심사만 가능, 반드시 5개여야 함")),
                                    fieldWithPath("isAllowingMarketingInfo").type(JsonFieldType.BOOLEAN)
                                            .description("마케팅 정보 수신 동의 여부")
                                            .attributes(getConstraints("constraints", "true 수신 동의, false 수신 거부"))
                            )
                    ))
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Nested
    class 닉네임_중복_체크 {

        @Test
        void 닉네임_중복_여부를_확인할_수_있다() {
            given(memberService.isDuplicate("nickname")).willReturn(true);

            docsGiven.contentType(MediaType.APPLICATION_JSON_VALUE)
                    .pathParam("nickname", "nickname")
                    .when().get("/api/members/duplicate/{nickname}")
                    .then().log().all()
                    .apply(document("member/duplicate-nickname-check/success",
                            pathParameters(parameterWithName("nickname").description("중복 검사할 닉네임")),
                            responseFields(
                                    fieldWithPath("isDuplicate").type(JsonFieldType.BOOLEAN).description("중복 여부"))
                    ))
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Nested
    class 회원_프로필_이미지_업로드 {

        @Test
        void 회원_프로필_이미지를_업로드_할_수_있다() throws IOException {
            File fakeImage = createFakeImage();
            given(memberService.uploadProfileImages(any(), any())).willReturn(
                    List.of("https://cdn.net/" + UUID.randomUUID() + ".png"));
            given(tokenProvider.extractSubject("jwt.token.here")).willReturn("1");
            given(tokenProvider.extractAuthority("jwt.token.here")).willReturn(Authority.MEMBER);
            given(authenticatoinContext.getPrincipal()).willReturn("1");

            docsGiven.contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.token.here")
                    .multiPart("images", fakeImage, "image/png")
                    .when().patch("/api/members/upload-profile-images")
                    .then().log().all()
                    .apply(document("member/upload-profile-images/success",
                            requestParts(
                                    partWithName("images").description("업로드할 프로필 이미지에 대한 MultipartFile")
                            ),
                            responseFields(
                                    fieldWithPath("imageUrls").type(JsonFieldType.ARRAY).description("저장된 Image Url")
                            )
                    ))
                    .statusCode(HttpStatus.OK.value());
        }
    }
}
