package com.project1hour.api.core.documentation;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import com.project1hour.api.core.domain.member.Authority;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class BungaeDocument extends DocumentationTest {

    @Nested
    class 만남_혹은_채팅_번개_모든_카테고리_찾기 {

        @Test
        void 사용자가_원하는_타입의_번개에_해당되는_모든_카테고리를_찾는다() {
            given(tokenProvider.extractSubject("jwt.token.here")).willReturn("1");
            given(tokenProvider.extractAuthority("jwt.token.here")).willReturn(Authority.MEMBER);
            given(authenticationContext.getPrincipal()).willReturn("1");

            docsGiven.contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.token.here")
                    .queryParam("bungaeType", "MEET")
                    .when().get("/api/bungaes/categories")
                    .then().log().all()
                    .apply(document("bunages/findCategories/success",
                            queryParameters(
                                    parameterWithName("bungaeType").description(
                                            "전체 카테고리를 조회할 번개 타입('MEET', 'CHAT' 중 하나이며, 앞 뒤 공백이 없어야 합니다.)")
                            ),
                            responseFields(
                                    fieldWithPath("categoryNames").type(JsonFieldType.ARRAY).description("카테고리 이름")
                            )
                    ))
                    .statusCode(HttpStatus.OK.value());
        }
    }

}
