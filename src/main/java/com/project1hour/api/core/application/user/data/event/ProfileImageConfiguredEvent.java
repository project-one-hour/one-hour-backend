package com.project1hour.api.core.application.user.data.event;

import com.project1hour.api.core.domain.user.UserDomainEvent;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DomainEvent : 프로필 사진을 설정했다
 */
public record ProfileImageConfiguredEvent(
        List<InputStream> images,
        List<Long> imageIds,
        LocalDateTime occurredAt) implements UserDomainEvent {

    public ProfileImageConfiguredEvent(final List<InputStream> images, final List<Long> imageIds) {
        this(images, imageIds, LocalDateTime.now());
    }
}
