package com.project1hour.api.core.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "image")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE image SET deleted_at = now() WHERE image_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class ImageEntity extends BaseEntity implements Persistable<Long> {

    @Id
    @Column(name = "image_id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @Column(name = "image_name", length = 50, nullable = false, unique = true)
    private String imageName;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @PostPersist
    @PostLoad
    private void markNotNew() {
        this.isNew = false;
    }
}
