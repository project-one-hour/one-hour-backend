package com.project1hour.api.core.application.user.model.event;

import com.project1hour.api.core.domain.image.value.ImageId;
import com.project1hour.api.core.domain.user.UserDomainEvent;
import java.io.InputStream;
import java.time.LocalDateTime;

public record ProfileImageConfiguredEvent(
        ImageId imageId,
        InputStream imageInput,
        LocalDateTime occurredAt
) implements UserDomainEvent {

    public ProfileImageConfiguredEvent(final ImageId imageId, final InputStream imageInput) {
        this(imageId, imageInput, LocalDateTime.now());
    }
}
