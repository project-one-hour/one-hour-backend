package com.project1hour.api.core.infrastructure.persistence;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Polygon;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "bungae")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE bungae SET deleted_at = now() WHERE bungae_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class BungaeEntity extends BaseEntity {

    @Id
    @Tsid
    @Column(name = "bungae_id")
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", unique = true)
    private ImageEntity imageEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Lob
    @Column(name = "bungae_description")
    private String bungaeDescription;

    @Column(name = "age_lower_limit", nullable = false)
    private Integer ageLowerLimit;

    @Column(name = "age_upper_limit", nullable = false)
    private Integer ageUpperLimit;

    @Column(name = "gender_type", nullable = false)
    private String genderType;

    @Column(name = "required_manner_user", nullable = false)
    private String requiredMannerUser;

    @Column(name = "bungae_type")
    private String bungaeType;

    @Column(name = "bungae_status")
    private String bungaeStatus;

    @Column(name = "location_description")
    private String locationDescription;

    @Column(columnDefinition = "POINT")
    private Polygon<G2D> location;

    @Column(nullable = false, updatable = false)
    private LocalDateTime startAt;
}
