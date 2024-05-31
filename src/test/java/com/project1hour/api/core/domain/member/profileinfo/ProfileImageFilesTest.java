package com.project1hour.api.core.domain.member.profileinfo;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProfileImageFilesTest {

    @Test
    void 프로필_이미지_파일을_생성할_수_있다() {
        // expect
        assertThatNoException().isThrownBy(
                () -> new ProfileImageFiles(List.of(
                        new MockMultipartFile("name1", "image1.jpg", "image/jpg", new byte[10]),
                        new MockMultipartFile("name2", "image2.jpg", "image/jpg", new byte[10]),
                        new MockMultipartFile("name3", "image3.jpg", "image/jpg", new byte[10])))
        );
    }

    @ParameterizedTest
    @MethodSource("getProfileImageUrls")
    void 프로필_이미지_파일이_없거나_3장을_넘기면_예외가_발생한다(List<MultipartFile> profileImageUrls) {
        // expect
        assertThatThrownBy(() -> new ProfileImageFiles(profileImageUrls));
    }

    private static Stream<Arguments> getProfileImageUrls() {
        return Stream.of(
                Arguments.arguments(Collections.emptyList()),
                Arguments.arguments(
                        List.of(
                                new MockMultipartFile("name1", "image1.jpg", "image/jpg", new byte[10]),
                                new MockMultipartFile("name2", "image2.jpg", "image/jpg", new byte[10]),
                                new MockMultipartFile("name3", "image3.jpg", "image/jpg", new byte[10]),
                                new MockMultipartFile("name4", "image4.jpg", "image/jpg", new byte[10])
                        )
                )
        );
    }
}
