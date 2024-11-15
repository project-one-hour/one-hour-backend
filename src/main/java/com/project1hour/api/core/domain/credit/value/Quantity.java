package com.project1hour.api.core.domain.credit.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Quantity(@Column(name = "quantity") int value) {

    public static Quantity fromEarnType(final EarnType earnType) {
        return new Quantity(earnType.getCreditVolume());
    }
}
