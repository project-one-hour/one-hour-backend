package com.project1hour.api.core.domain.user.entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.project1hour.api.global.advice.BadRequestException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UserInterestTest {

    @Nested
    class UserInterests_일급_컬랙션은 {

        @ParameterizedTest
        @ValueSource(ints = {4, 6})
        void 관심사_ID가_5개_미만이거나_초과하면_예외가_발생한다(int size) {
            //given
            Set<Long> interestIds = LongStream.range(0, size).boxed().collect(Collectors.toSet());

            //expect
            assertThatThrownBy(() ->
                    UserInterests.builder()
                            .interestIds(interestIds)
                            .build())
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(String.format("관심사가 5개가 아닙니다. size = %d", size));
        }


        @Test
        void 관심사_ID가_Null이면_예외가_발생한다() {
            //given
            Set<Long> interestIds = null;

            //expect
            assertThatThrownBy(() ->
                    UserInterests.builder()
                            .interestIds(interestIds)
                            .build())
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("관심사가 비어있습니다.");
        }

        @Test
        void 관심사_ID가_비어있으면_예외가_발생한다() {
            //given
            Set<Long> interestIds = Collections.emptySet();

            //expect
            assertThatThrownBy(() ->
                    UserInterests.builder()
                            .interestIds(interestIds)
                            .build())
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("관심사가 비어있습니다.");
        }
    }
}