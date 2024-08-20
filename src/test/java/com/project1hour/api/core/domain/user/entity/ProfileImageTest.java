package com.project1hour.api.core.domain.user.entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.project1hour.api.global.advice.BadRequestException;
import java.util.List;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ProfileImageTest {

    @Nested
    class ProfileImages_일급_컬랙션은 {

        @ParameterizedTest
        @ValueSource(ints = {0, 4})
        void 프로필_이미지_ID를_4장_이상이거나_없을_경우_예외가_발생한다(int size) {
            //given
            List<Long> profileImageIds = LongStream.range(0, size).boxed().toList();
            int tmpPrimaryImageIndex = 0;

            //expected
            assertThatThrownBy(() ->
                    ProfileImages.builder()
                            .imageIds(profileImageIds)
                            .primaryImageIndex(tmpPrimaryImageIndex)
                            .build())
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("프로필 이미지는 1장 이상 3장 이하여야 합니다. size = " + profileImageIds.size());
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 3})
        void 대표_프로필_이미지_index가_프로필_이미지_ID_크기보다_작거나_0보다_작을_때_예외가_발생한다(int index) {
            //given
            List<Long> profileImageIds = LongStream.range(0, 2).boxed().toList();

            //expect
            assertThatThrownBy(() ->
                    ProfileImages.builder()
                            .imageIds(profileImageIds)
                            .primaryImageIndex(index)
                            .build())
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(
                            String.format("유효하지 않은 대표 프로필 사진 index입니다. index = %d image size = %d",
                                    index, profileImageIds.size()));
        }
    }
}
