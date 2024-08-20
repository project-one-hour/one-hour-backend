package com.project1hour.api.global.domain;

import java.time.LocalDateTime;

public interface DomainEvent {
    LocalDateTime occurredAt();
}
