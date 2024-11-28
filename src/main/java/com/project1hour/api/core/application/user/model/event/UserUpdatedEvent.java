package com.project1hour.api.core.application.user.model.event;

import com.project1hour.api.core.domain.user.UserDomainEvent;
import com.project1hour.api.core.domain.user.value.UserId;
import java.time.LocalDateTime;

public record UserUpdatedEvent(UserId userId, boolean isNew, LocalDateTime occurredAt) implements UserDomainEvent {

    public UserUpdatedEvent(final UserId userId, final boolean isNew) {
        this(userId, isNew, LocalDateTime.now());
    }

    public UserUpdatedEvent(final UserId userId) {
        this(userId, false);
    }
}
