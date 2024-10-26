package com.project1hour.api.core.domain.user;

import java.time.LocalDateTime;

public interface UserDomainEvent {
    LocalDateTime occurredAt();
}
