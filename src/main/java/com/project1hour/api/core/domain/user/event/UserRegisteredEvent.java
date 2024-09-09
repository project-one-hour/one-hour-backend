package com.project1hour.api.core.domain.user.event;

import java.time.LocalDateTime;

public record UserRegisteredEvent(Long userId, LocalDateTime occurredAt) implements UserDomainEvent {

    public UserRegisteredEvent(Long userId) {
        this(userId, LocalDateTime.now());
    }
}
