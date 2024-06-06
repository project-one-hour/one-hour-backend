package com.project1hour.api.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Embeddable
public class CommonField {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(insertable = false)
    private Long updatedBy;

    @Column(insertable = false)
    private LocalDateTime deletedAt;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommonField that = (CommonField) o;
        return Objects.equals(createdAt, that.createdAt) && Objects.equals(createdBy, that.createdBy)
                && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(updatedBy,
                that.updatedBy) && Objects.equals(deletedAt, that.deletedAt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(createdAt);
        result = 31 * result + Objects.hashCode(createdBy);
        result = 31 * result + Objects.hashCode(updatedAt);
        result = 31 * result + Objects.hashCode(updatedBy);
        result = 31 * result + Objects.hashCode(deletedAt);
        return result;
    }
}
