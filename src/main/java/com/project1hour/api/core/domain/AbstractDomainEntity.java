package com.project1hour.api.core.domain;

import java.util.Objects;
import lombok.Getter;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
public abstract class AbstractDomainEntity<ID> {

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        return other instanceof AbstractDomainEntity<?> entity &&
                Objects.equals(this.getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public abstract ID getId();
}
