package com.project1hour.api.core.domain.user.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;

/**
 * Policy : 추후 값 변경 X
 */
@Embeddable
public record Birthday(
        @Column(name = "birthday", updatable = false)
        LocalDate value
) {
}
