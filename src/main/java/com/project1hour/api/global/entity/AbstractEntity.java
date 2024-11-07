package com.project1hour.api.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity<ID> implements Persistable<ID> {

    @LastModifiedDate
    @Column(insertable = false)
    LocalDateTime updatedAt;

    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdAt;

    @Column(insertable = false)
    LocalDateTime deletedAt;

    @Override
    public boolean isNew() {
        return Objects.isNull(getCreatedAt());
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
