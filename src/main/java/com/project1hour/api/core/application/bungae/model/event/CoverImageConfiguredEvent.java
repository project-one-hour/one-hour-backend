package com.project1hour.api.core.application.bungae.model.event;

import com.project1hour.api.core.domain.bungae.BungaeDomainEvent;
import java.io.InputStream;
import java.time.LocalDateTime;

public record CoverImageConfiguredEvent(
        Long imageId,
        InputStream imageInput,
        LocalDateTime occurredAt
) implements BungaeDomainEvent {

    public CoverImageConfiguredEvent(final Long imageId, final InputStream imageInput) {
        this(imageId, imageInput, LocalDateTime.now());
    }
}
