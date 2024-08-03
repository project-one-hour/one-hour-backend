package com.project1hour.api.global.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.util.Objects;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity<ID> {

    @Embedded
    private DateAuditInfo dateAuditInfo;

    @Embedded
    private DeleteInfo deleteInfo;

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        AbstractEntity<?> entity = (AbstractEntity<?>) other;
        return Objects.equals(this.getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hashCode(this.getId());
    }

    public abstract ID getId();
}
