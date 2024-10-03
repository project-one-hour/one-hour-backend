package com.project1hour.api.core.domain.image.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record ImageName(
        @Column(name = "image_name", length = 50, nullable = false, unique = true)
        String value
) {
}
