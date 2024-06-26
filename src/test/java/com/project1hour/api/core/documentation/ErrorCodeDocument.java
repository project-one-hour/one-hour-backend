package com.project1hour.api.core.documentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.project1hour.api.core.documentation.errorcode.ErrorCodeFieldsSnippet;
import org.junit.jupiter.api.Test;

public class ErrorCodeDocument extends DocumentationTest {

    @Test
    void 에러_코드를_반환한다() {
        ErrorCodeFieldsSnippet errorCodeFieldsSnippet = new ErrorCodeFieldsSnippet("error-code", "error-code-template");

        docsGiven.when().get("/test/error-code")
                .then().log().all()
                .apply(document("error-code", errorCodeFieldsSnippet));
    }
}
