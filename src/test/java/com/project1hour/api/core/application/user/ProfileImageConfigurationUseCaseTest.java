package com.project1hour.api.core.application.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.project1hour.api.core.application.user.api.ProfileImageClient;
import com.project1hour.api.core.application.user.component.TestUserEventListener;
import com.project1hour.api.global.advice.BadRequestException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class ProfileImageConfigurationUseCaseTest {

    @Autowired
    private ProfileImageConfigurationUseCase profileImageConfigurationUseCase;

    @Autowired
    private TestUserEventListener testUserEventListener;

    @MockBean
    private ProfileImageClient profileImageClient;

    @BeforeEach
    void beforeEach() {
        testUserEventListener.reset();
    }

    @Test
    void 유효하지_않은_프로필_이미지가_존재할_때_예외가_발생한다() {
        // given
        byte[] dummyData = "잘못된 이미지 파일 두둥".getBytes();
        List<InputStream> dummyImages = List.of(new ByteArrayInputStream(dummyData));
        given(profileImageClient.hasInvalidImages(dummyImages)).willReturn(true);

        // expect
        assertThatThrownBy(() -> profileImageConfigurationUseCase.configureProfileImages(dummyImages))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("유효하지 않은 프로필 이미지가 포함되어 있습니다.");
    }

    @ParameterizedTest
    @MethodSource("getDummyImages")
    void 프로필_이미지를_이미지_ID로_변환할_수_있다(List<InputStream> dummyImages) {
        // given
        given(profileImageClient.hasInvalidImages(dummyImages)).willReturn(false);

        //when
        List<Long> result = profileImageConfigurationUseCase.configureProfileImages(dummyImages);

        // then
        assertThat(result).hasSize(dummyImages.size());
        assertThat(testUserEventListener.getEvents())
                .hasSize(1).first()
                .hasFieldOrProperty("images")
                .hasFieldOrProperty("imageIds")
                .hasFieldOrProperty("occurredAt");
    }

    private static Stream<Arguments> getDummyImages() {
        byte[] dummyData = "더미 이미지 파일 두둥".getBytes();
        return Stream.of(
                Arguments.arguments(
                        List.of(new ByteArrayInputStream(dummyData))
                ),
                Arguments.arguments(
                        List.of(
                                new ByteArrayInputStream(dummyData),
                                new ByteArrayInputStream(dummyData)
                        )
                ),
                Arguments.arguments(
                        List.of(
                                new ByteArrayInputStream(dummyData),
                                new ByteArrayInputStream(dummyData),
                                new ByteArrayInputStream(dummyData)
                        )
                )
        );
    }
}
