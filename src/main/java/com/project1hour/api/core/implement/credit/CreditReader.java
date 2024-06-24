package com.project1hour.api.core.implement.credit;

import com.project1hour.api.core.domain.credit.Credit;
import com.project1hour.api.core.domain.credit.CreditRepository;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditReader {

    private final CreditRepository creditRepository;

    public Credit read(Long memberId) {
        return creditRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BadRequestException("현재 사용자의 크레딧을 찾을 수 없습니다.", ErrorCode.CREDIT_NOT_FOUND));
    }
}
