package com.project1hour.api.global.domain;

import jakarta.persistence.Column;
import java.time.LocalDateTime;

public record DeleteInfo(
        @Column(insertable = false)
        LocalDateTime deletedAt
) {
}
