package com.project1hour.api.core.implement.credit;

import com.project1hour.api.core.domain.credit.Credit;
import com.project1hour.api.core.domain.credit.CreditRepository;
import com.project1hour.api.core.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class CreditProcessor {

    private final CreditRepository creditRepository;

    public void giveWelcomeCredit(final Member savedMember) {
        Credit newMemberCredit = Credit.createWelcomeCredit(savedMember);
        creditRepository.save(newMemberCredit);
    }
}
