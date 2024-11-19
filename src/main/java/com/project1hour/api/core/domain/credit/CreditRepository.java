package com.project1hour.api.core.domain.credit;

import com.project1hour.api.core.domain.credit.entity.Credit;

public interface CreditRepository {
    Credit save(Credit credit);
}
