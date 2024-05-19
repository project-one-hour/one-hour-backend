package com.project1hour.api.core.domain.member.profileinfo;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Birthday {

    private static LocalDate MINIMUM_ADULT_BIRTH = LocalDate.of(
            LocalDate.now().minusYears(9).getYear(),
            LocalDate.MAX.getMonth(),
            LocalDate.MAX.getDayOfMonth()
    );

    @Column(name = "birthday")
    private LocalDate value;

    public Birthday(final LocalDate value) {
        validate(value);
        this.value = value;
    }

    private void validate(final LocalDate value) {
        if (MINIMUM_ADULT_BIRTH.isBefore(value)) {
            String message = String.format("성인만 가입할 수 있습니다. 최소년생 = %d", MINIMUM_ADULT_BIRTH.getYear());
            throw new BadRequestException(message, ErrorCode.INVALID_AGE_LIMIT);
        }
    }
}
