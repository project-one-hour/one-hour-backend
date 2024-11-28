package com.project1hour.api.core.infrastructure.mapper;

import com.project1hour.api.core.domain.image.entity.Image;
import com.project1hour.api.core.domain.image.value.ImageId;
import com.project1hour.api.core.domain.image.value.ImageName;
import com.project1hour.api.core.infrastructure.persistence.ImageEntity;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    public ImageEntity toEntity(final Image image) {
        return ImageEntity.builder()
                .id(image.getId().id())
                .imageName(image.getImageName().value())
                .imagePath(image.getImagePath())
                .build();
    }

    public Image toDomain(final ImageEntity imageEntity) {
        return Image.builder()
                .id(new ImageId(imageEntity.getId()))
                .imagePath(imageEntity.getImagePath())
                .imageName(new ImageName(imageEntity.getImageName()))
                .build();
    }
}
