package com.project1hour.api.core.domain.image.entity;

import com.project1hour.api.core.domain.image.value.ImageName;
import com.project1hour.api.global.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
@SQLDelete(sql = "UPDATE image SET deleted_at = now() WHERE image_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends AbstractEntity<Long> {

    @Id
    @Column(name = "image_id")
    private Long id;

    @Embedded
    private ImageName imageName;

    @Builder
    public Image(final Long id, final ImageName imageName,
                 final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.imageName = imageName;
        super.createdAt = createdAt;
        super.updatedAt = updatedAt;
    }
}
