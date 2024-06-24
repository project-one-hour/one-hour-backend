package com.project1hour.api.core.application.credit;

import com.project1hour.api.core.domain.credit.Credit;
import com.project1hour.api.core.implement.credit.CreditReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditReader creditReader;

    public boolean canUseCredit(final Long memberId, final int creditCount) {
        Credit credit = creditReader.read(memberId);

        return credit.hasMoreCredit(creditCount);
    }
}
