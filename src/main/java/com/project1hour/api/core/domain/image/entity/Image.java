package com.project1hour.api.core.domain.image.entity;

import com.project1hour.api.core.domain.image.value.ImageName;
import com.project1hour.api.global.domain.AbstractEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE user SET deleted_at = now() WHERE user_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends AbstractEntity<Long> {

    @Id
    private Long id;

    @Embedded
    private ImageName imageName;

    @Builder(builderClassName = "CreateImageBuilder", builderMethodName = "createImage")
    public Image(Long id, ImageName imageName) {
        this.id = id;
        this.imageName = imageName;
    }
}
