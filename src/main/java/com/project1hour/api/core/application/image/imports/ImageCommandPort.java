package com.project1hour.api.core.application.image.imports;

import com.project1hour.api.core.domain.image.entity.Image;

public interface ImageCommandPort {
    Image saveImage(Image image);
}
