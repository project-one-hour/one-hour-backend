package com.project1hour.api.core.domain.credit.entity;

import com.project1hour.api.core.domain.credit.value.EarnType;
import com.project1hour.api.core.domain.credit.value.Quantity;
import com.project1hour.api.global.entity.AbstractEntity;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE credit SET deleted_at = now() WHERE credit_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Credit extends AbstractEntity<Long> {

    @Id
    @Tsid
    @Column(name = "credit_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private EarnType earnType;

    @Embedded
    private Quantity quantity;

    @Column(nullable = false)
    private Long userId;

    @Builder
    public Credit(final Long id, final EarnType earnType, final Quantity quantity, final Long userId,
                  final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.earnType = earnType;
        this.quantity = quantity;
        this.userId = userId;
        super.createdAt = createdAt;
        super.updatedAt = updatedAt;
    }

    public static Credit createCredit(final Long userId, final EarnType earnType) {
        return Credit.builder()
                .userId(userId)
                .earnType(earnType)
                .quantity(Quantity.fromEarnType(earnType))
                .build();
    }
}
