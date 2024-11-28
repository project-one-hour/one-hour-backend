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
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(
        name = "participant",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "bungae_id"})
)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE participant SET deleted_at = now() WHERE participant_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class ParticipantEntity extends BaseEntity {

    @Id
    @Tsid
    @Column(name = "participant_id")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bungae_id")
    private BungaeEntity bungae;

    @Column(name = "bungae_role", nullable = false)
    private String bungaeRole;
}
