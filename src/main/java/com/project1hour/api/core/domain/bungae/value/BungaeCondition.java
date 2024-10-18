package com.project1hour.api.core.domain.bungae.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Arrays;

@Embeddable
public record BungaeCondition(
        @Enumerated(value = EnumType.STRING)
        @Column(nullable = false)
        GenderType genderType,

        @Column(nullable = false)
        int ageLowerLimit,

        @Column(nullable = false)
        int ageUpperLimit,

        @Enumerated(value = EnumType.STRING)
        @Column(nullable = false)
        MannerLevel mannerLevelLowerBound
) {

    public enum GenderType {NONE, FEMALE, MALE}

    public enum MannerLevel {LEVEL0, LEVEL1, LEVEL2, LEVEL3}

    private static final int MIN_AGE_VALUE = 18;
    private static final int MAX_AGE_VALUE = 44;

    public static MannerLevel mannerLevelFromOrdinal(int ordinal) {
        return Arrays.stream(MannerLevel.values())
                .filter(mannerLevel -> mannerLevel.ordinal() == ordinal)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid manner level: " + ordinal));
    }
}
