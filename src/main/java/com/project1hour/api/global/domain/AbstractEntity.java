package com.project1hour.api.global.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.util.Objects;
import lombok.Getter;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity<ID> implements Persistable<ID> {

    @Embedded
    private DateAuditInfo dateAuditInfo;

    @Embedded
    private DeleteInfo deleteInfo;

    @Override
    public boolean isNew() {
        return Objects.isNull(getDateAuditInfo().createdAt());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) {
            return false;
        }

        AbstractEntity<?> entity = (AbstractEntity<?>) other;
        return Objects.equals(this.getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public abstract ID getId();
}
