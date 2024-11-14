package com.project1hour.api.core.domain.credit;

import java.time.LocalDateTime;

public interface CreditDomainEvent {
    LocalDateTime occurredAt();
}
