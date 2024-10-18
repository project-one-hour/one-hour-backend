package com.project1hour.api.core.domain.bungae.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;

@Embeddable
public record Description(
        @Lob
        @Column(name = "description")
        String value
) {
}
