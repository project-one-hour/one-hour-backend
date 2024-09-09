package com.project1hour.api.core.infrastructure.image;

import com.project1hour.api.core.domain.image.ImageRepository;
import com.project1hour.api.core.domain.image.entity.Image;
import com.project1hour.api.core.infrastructure.image.jpa.JpaImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DefaultImageRepository implements ImageRepository {

    private final JpaImageRepository jpaImageRepository;

    @Override
    public Image save(final Image uploadedImage) {
        return jpaImageRepository.save(uploadedImage);
    }
}
