package com.project1hour.api.core.domain.image.entity;

import com.project1hour.api.core.domain.AbstractDomainEntity;
import com.project1hour.api.core.domain.image.value.ImageId;
import com.project1hour.api.core.domain.image.value.ImageName;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Image extends AbstractDomainEntity<ImageId> {

    private final ImageId id;

    private String imagePath;

    private ImageName imageName;

    @Builder
    public Image(final ImageId id, final String imagePath, final ImageName imageName) {
        this.id = id;
        this.imagePath = imagePath;
        this.imageName = imageName;
    }
}
