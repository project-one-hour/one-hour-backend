package com.project1hour.api.core.documentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class InterestDocument extends DocumentationTest {


    @Nested
    class 전체_관심사_조회 {

        @Test
        void 회원가입시_필요한_전체_관심사를_조회한다() {
            docsGiven.contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/api/interests")
                    .then().log().all()
                    .apply(document("interest/getAll/success",
                            responseFields(
                                    fieldWithPath("interestNames").type(JsonFieldType.ARRAY).description("관심사 이름")
                            )
                    ))
                    .statusCode(HttpStatus.OK.value());
        }
    }
}
