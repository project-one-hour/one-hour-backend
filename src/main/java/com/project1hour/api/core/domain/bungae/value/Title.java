package com.project1hour.api.core.domain.bungae.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Title(
        @Column(nullable = false, length = 40)
        String value
) {
}
