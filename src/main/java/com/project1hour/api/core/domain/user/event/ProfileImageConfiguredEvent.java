package com.project1hour.api.core.domain.user.event;

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

    public ProfileImageConfiguredEvent(List<InputStream> images, List<Long> imageIds) {
        this(images, imageIds, LocalDateTime.now());
    }
}
