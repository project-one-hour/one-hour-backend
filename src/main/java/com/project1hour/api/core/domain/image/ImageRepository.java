package com.project1hour.api.core.domain.image;

import com.project1hour.api.core.domain.image.entity.Image;

public interface ImageRepository {
    Image save(Image uploadedImage);
}
