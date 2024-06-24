package com.project1hour.api.core.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import com.project1hour.api.core.domain.member.Authority;
import com.project1hour.api.core.presentation.credit.dto.CreditCheckRequest;
import java.io.IOException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class CreditDocument extends DocumentationTest {

    @Nested
    class 사용가능_재화_확인 {

        @Test
        void 사용자가_특정_갯수의_재화를_사용할_수_있는지_확인한다() throws IOException {
            CreditCheckRequest request = new CreditCheckRequest(6);
            given(creditService.canUseCredit(any(), anyInt())).willReturn(true);
            given(tokenProvider.extractSubject("jwt.token.here")).willReturn("1");
            given(tokenProvider.extractAuthority("jwt.token.here")).willReturn(Authority.MEMBER);
            given(authenticationContext.getPrincipal()).willReturn("1");

            docsGiven.contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.token.here")
                    .body(request)
                    .when().post("/api/credits/check")
                    .then().log().all()
                    .apply(document("credit/count-check/success",
                            requestFields(
                                    fieldWithPath("creditCount").type(JsonFieldType.NUMBER).description("사용할 재화의 갯수")
                            ),
                            responseFields(
                                    fieldWithPath("canUse").type(JsonFieldType.BOOLEAN)
                                            .description("해당 사용자가 입력받은 재화를 사용가능한지 여부(true:가능, false:불가능")
                            )
                    ))
                    .statusCode(HttpStatus.OK.value());
        }
    }
}
