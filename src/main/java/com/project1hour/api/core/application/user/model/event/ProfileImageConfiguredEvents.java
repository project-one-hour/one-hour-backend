package com.project1hour.api.core.application.user.model.event;

import com.project1hour.api.core.domain.user.UserDomainEvent;
import java.time.LocalDateTime;
import java.util.List;

public record ProfileImageConfiguredEvents(
        List<ProfileImageConfiguredEvent> value,
        LocalDateTime occurredAt
) implements UserDomainEvent {

    public ProfileImageConfiguredEvents(final List<ProfileImageConfiguredEvent> eventList) {
        this(eventList, LocalDateTime.now());
    }
}
