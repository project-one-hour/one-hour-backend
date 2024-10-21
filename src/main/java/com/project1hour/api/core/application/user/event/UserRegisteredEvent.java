package com.project1hour.api.core.application.user.event;

import com.project1hour.api.core.domain.user.UserDomainEvent;
import java.time.LocalDateTime;

public record UserRegisteredEvent(Long userId, LocalDateTime occurredAt) implements UserDomainEvent {

    public UserRegisteredEvent(final Long userId) {
        this(userId, LocalDateTime.now());
    }
}
