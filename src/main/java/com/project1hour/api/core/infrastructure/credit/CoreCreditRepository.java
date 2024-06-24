package com.project1hour.api.core.infrastructure.credit;

import com.project1hour.api.core.domain.credit.Credit;
import com.project1hour.api.core.domain.credit.CreditRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CoreCreditRepository implements CreditRepository {

    private final JpaCreditRepository jpaCreditRepository;

    @Override
    public Credit save(final Credit credit) {
        return jpaCreditRepository.save(credit);
    }

    @Override
    public Optional<Credit> findByMemberId(Long memberId) {
        return jpaCreditRepository.findByMemberId(memberId);
    }
}
