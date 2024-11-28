package com.project1hour.api.core.application.user.model.event;

import com.project1hour.api.core.domain.user.UserDomainEvent;
import com.project1hour.api.core.domain.user.value.UserId;
import java.time.LocalDateTime;

public record UserRegisteredEvent(UserId newUserId, LocalDateTime occurredAt) implements UserDomainEvent {

    public UserRegisteredEvent(final UserId newUserId) {
        this(newUserId, LocalDateTime.now());
    }
}
