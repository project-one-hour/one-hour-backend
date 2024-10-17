package com.project1hour.api.core.domain.bungae.value;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;

public record Description(
        @Lob @Column
        String value
) {
}
