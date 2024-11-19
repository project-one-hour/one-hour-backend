package com.project1hour.api.core.application.user.model.event;

import com.project1hour.api.core.domain.user.UserDomainEvent;
import java.time.LocalDateTime;

public record UserRegisteredEvent(Long newUserId, LocalDateTime occurredAt) implements UserDomainEvent {

    public UserRegisteredEvent(final Long newUserId) {
        this(newUserId, LocalDateTime.now());
    }
}
