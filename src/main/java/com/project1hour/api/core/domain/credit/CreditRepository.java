package com.project1hour.api.core.domain.credit;

import java.util.Optional;

public interface CreditRepository {

    Credit save(Credit credit);

    Optional<Credit> findByMemberId(Long memberId);
}
