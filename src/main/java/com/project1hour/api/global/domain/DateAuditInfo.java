package com.project1hour.api.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Embeddable
public record DateAuditInfo(
        @CreatedDate
        @Column(updatable = false)
        LocalDateTime createdAt,

        @LastModifiedDate
        @Column(insertable = false)
        LocalDateTime updatedAt
) {
}
