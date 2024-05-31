package com.project1hour.api.core.infrastructure.image;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.project1hour.api.core.domain.image.ImageFile;
import com.project1hour.api.core.domain.image.ImageUploader;
import com.project1hour.api.global.advice.InfraStructureException;
import io.awspring.cloud.s3.S3Exception;
import io.awspring.cloud.s3.S3Template;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class S3ImageUploaderTest {

    @Autowired
    private ImageUploader imageUploader;

    @MockBean
    private S3Template s3Template;

    @Nested
    class upload_메소드는 {

        @Test
        void S3에_이미지를_업로드할_수_없으면_예외가_발생한다() {
            // given
            given(s3Template.upload(any(), any(), any())).willThrow(S3Exception.class);
            ImageFile imageFile = new ImageFile("image.png", "image/png", "png",
                    new ByteArrayInputStream("image.png".getBytes()));

            // expect
            assertThatThrownBy(() -> imageUploader.upload(imageFile))
                    .isInstanceOf(InfraStructureException.class)
                    .hasMessage("S3 버킷에 이미지를 업로드할 수 없습니다.");

        }
    }
}
