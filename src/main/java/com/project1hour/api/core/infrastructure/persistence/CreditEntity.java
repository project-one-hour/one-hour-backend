package com.project1hour.api.core.infrastructure.persistence;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "credit")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE credit SET deleted_at = now() WHERE credit_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class CreditEntity extends BaseEntity {

    @Id
    @Tsid
    @Column(name = "credit_id")
    @EqualsAndHashCode.Include
    private Long id;

    @With
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "earn_type")
    private String earnType;

    @Column(name = "quantity")
    private Integer quantity;
}
