package com.project1hour.api.core.domain.image;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.project1hour.api.global.advice.BadRequestException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ImageFileTest {

    @Test
    void 이미지_파일이_NULL이라면_예외가_발생한다() {
        // expect
        assertThatThrownBy(() -> ImageFile.from(null))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("이미지로 NULL값이 들어올 수 없습니다.");
    }

    @Test
    void 이미지_파일이_비어있다면_예외가_발생한다() {
        // given
        MockMultipartFile file = new MockMultipartFile("name1", "image1.jpg", "image/jpg", new byte[0]);

        // expect
        assertThatThrownBy(() -> ImageFile.from(file))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("이미지 파일이 비어있을 수 없습니다.");
    }

    @Test
    void 이미지_파일의_이름이_비어있으면_예외가_발생한다() {
        // given
        MockMultipartFile file = new MockMultipartFile("image1", "", "image/jpg", new byte[10]);

        // expect
        assertThatThrownBy(() -> ImageFile.from(file))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("이미지의 이름이 NULL이거나 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"image.gif", "image.webp", "image.dummy"})
    void 지원하지_않는_이미지_확장자라면_예외가_발생한다(String originalFileName) {
        // given
        String extension = StringUtils.getFilenameExtension(originalFileName);
        MockMultipartFile file = new MockMultipartFile("name", originalFileName, "image/" + extension,
                originalFileName.getBytes());

        // expect
        assertThatThrownBy(() -> ImageFile.from(file))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("불가능한 이미지 확장자 입니다. extension = " + extension);
    }
}
