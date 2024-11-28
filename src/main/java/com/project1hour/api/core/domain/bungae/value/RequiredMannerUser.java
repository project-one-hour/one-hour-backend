package com.project1hour.api.core.domain.bungae.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record RequiredMannerUser(
        @Column(name = "required_manner_user", nullable = false)
        boolean required
) {

    private static final int MINIMUM_REQUIRED_MANNER_LEVEL_VALUE = 2;
}
