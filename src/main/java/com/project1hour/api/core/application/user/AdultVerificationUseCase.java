package com.project1hour.api.core.application.user;


import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO  : 핸드폰 인증이 추가되면 대체 되거나 추가로 핸드폰 인증으로 성인을 인증해야 할 수 있음 <br/>
 * Command : 성인 확인
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdultVerificationUseCase {

    private static LocalDate MINIMUM_ADULT_BIRTH = LocalDate.of(
            LocalDate.now().minusYears(19).getYear(),
            LocalDate.MAX.getMonth(),
            LocalDate.MAX.getDayOfMonth()
    );

    public void checkIfAdult(final LocalDate birthday) {
        if (MINIMUM_ADULT_BIRTH.isBefore(birthday)) {
            String message = String.format("성인만 가입할 수 있습니다. 최소년생 = %d", MINIMUM_ADULT_BIRTH.getYear());
            throw new BadRequestException(message, ErrorCode.INVALID_AGE_LIMIT);
        }
    }
}
