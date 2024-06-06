package com.project1hour.api.core.documentation;

import static com.project1hour.api.FakeImageGenerator.createFakeImage;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;

import com.project1hour.api.core.domain.member.Authority;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

class ImageDocument extends DocumentationTest {

    @Nested
    class 이미지_업로드 {

        @Test
        void 이미지를_업로드_할_수_있다() throws IOException {
            File fakeImage = createFakeImage();
            given(imageUploader.upload(any())).willReturn("https://cdn.net/" + UUID.randomUUID() + ".png");
            given(tokenProvider.extractAuthority("jwt.token.here")).willReturn(Authority.MEMBER);
            given(authenticationContext.getPrincipal()).willReturn("1");

            docsGiven.contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.token.here")
                    .multiPart("images", fakeImage, "image/png")
                    .when().post("/api/images/upload")
                    .then().log().all()
                    .apply(document("image/upload/success",
                            requestParts(
                                    partWithName("images").description("업로드할 이미지에 대한 MultipartFile")
                            ),
                            responseFields(
                                    fieldWithPath("imageUrls").type(JsonFieldType.ARRAY).description("저장된 Image Url")
                            )
                    ))
                    .statusCode(HttpStatus.OK.value());
        }
    }
}
