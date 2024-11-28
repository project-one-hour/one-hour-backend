package com.project1hour.api.core.infrastructure.adapter;

import com.project1hour.api.core.application.image.imports.ImageCommandPort;
import com.project1hour.api.core.domain.image.entity.Image;
import com.project1hour.api.core.infrastructure.mapper.ImageMapper;
import com.project1hour.api.core.infrastructure.persistence.ImageEntity;
import com.project1hour.api.core.infrastructure.repository.JpaImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImagePersistenceAdapter implements ImageCommandPort {

    private final ImageMapper imageMapper;
    private final JpaImageRepository jpaImageRepository;

    @Override
    public Image saveImage(final Image image) {
        ImageEntity imageEntity = imageMapper.toEntity(image);
        ImageEntity savedImageEntity = jpaImageRepository.save(imageEntity);
        return imageMapper.toDomain(savedImageEntity);
    }
}
