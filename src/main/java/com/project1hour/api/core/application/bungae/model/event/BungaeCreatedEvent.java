package com.project1hour.api.core.application.bungae.model.event;

import com.project1hour.api.core.domain.bungae.BungaeDomainEvent;
import java.time.LocalDateTime;

public record BungaeCreatedEvent(Long newBungaeId, LocalDateTime occurredAt) implements BungaeDomainEvent {

    public BungaeCreatedEvent(final Long newBungaeId) {
        this(newBungaeId, LocalDateTime.now());
    }
}
