package com.project1hour.api.core.documentation;

import static com.project1hour.api.core.documentation.errorcode.ErrorCodeFieldsSnippet.errorCodeFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.project1hour.api.core.documentation.errorcode.FakeErrorCodeController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(FakeErrorCodeController.class)
public class ErrorCodeDocument extends RestDocsTestSupport {

    @Test
    @DisplayName("에러 코드를 반환한다")
    void errorCode() throws Exception {
        mockMvc().perform(get("/test/error-code"))
                .andDo(restDocs().document(
                        errorCodeFields()
                ));
    }
}
