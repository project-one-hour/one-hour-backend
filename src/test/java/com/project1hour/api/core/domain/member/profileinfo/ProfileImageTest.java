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

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProfileImageTest {

    @Test
    void 프로필_이미지를_생성할_수_있다() {
        // expect
        assertThatNoException().isThrownBy(() -> ProfileImage.from(
                List.of("https://cdn.net/image1.png", "https://cdn.net/image2.png", "https://cdn.net/image3.png")));
    }

    @ParameterizedTest
    @MethodSource("getProfileImageUrls")
    void 프로필_이미지가_없거나_3장을_넘기면_예외가_발생한다(List<String> profileImageUrls) {
        // expect
        assertThatThrownBy(() -> ProfileImage.from(profileImageUrls));
    }

    private static Stream<Arguments> getProfileImageUrls() {
        return Stream.of(
                Arguments.arguments(Collections.emptyList()),
                Arguments.arguments(List.of("https://cdn.net/image1.png", "https://cdn.net/image2.png",
                        "https://cdn.net/image3.png", "https://cdn.net/image4.png"))
        );
    }

}
