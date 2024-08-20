package com.project1hour.api.core.domain.user.value;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;

/**
 * Policy : 추후 값 변경 X
 */
@Embeddable
public record Birthday(
        @Column(name = "birthday", updatable = false)
        LocalDate value
) {

    private static LocalDate MINIMUM_ADULT_BIRTH = LocalDate.of(
            LocalDate.now().minusYears(19).getYear(),
            LocalDate.MAX.getMonth(),
            LocalDate.MAX.getDayOfMonth()
    );

    public Birthday {
        validate(value);
    }

    /**
     * Policy : 사용자는 성인(만 18세 이상)이어야 한다
     */
    private void validate(final LocalDate birthday) {
        if (MINIMUM_ADULT_BIRTH.isBefore(birthday)) {
            String message = String.format("성인만 가입할 수 있습니다. 최소년생 = %d", MINIMUM_ADULT_BIRTH.getYear());
            throw new BadRequestException(message, ErrorCode.INVALID_AGE_LIMIT);
        }
    }
}
