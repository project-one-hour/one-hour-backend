package com.project1hour.api.core.application.credit.imports;

import com.project1hour.api.core.domain.credit.entity.Credit;

public interface CreditCommandPort {

    Credit create(Credit credit);
}
