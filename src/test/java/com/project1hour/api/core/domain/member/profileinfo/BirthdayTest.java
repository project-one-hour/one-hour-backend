package com.project1hour.api.core.domain.member.profileinfo;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.project1hour.api.global.advice.BadRequestException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BirthdayTest {

    private static LocalDate MINIMUM_ADULT_BIRTH = LocalDate.of(
            LocalDate.now().minusYears(9).getYear(),
            LocalDate.MAX.getMonth(),
            LocalDate.MAX.getDayOfMonth()
    );

    @Test
    void 올해_기준_성인이라면_생성할_수_있다() {
        // expect
        assertThatNoException().isThrownBy(() -> {
            new Birthday(MINIMUM_ADULT_BIRTH);
        });
    }

    @Test
    void 미성년자라면_예외가_발생한다() {
        // expect
        assertThatThrownBy(() -> new Birthday(MINIMUM_ADULT_BIRTH.plusDays(1)))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(String.format("성인만 가입할 수 있습니다. 최소년생 = %d", MINIMUM_ADULT_BIRTH.getYear()));
    }
}
