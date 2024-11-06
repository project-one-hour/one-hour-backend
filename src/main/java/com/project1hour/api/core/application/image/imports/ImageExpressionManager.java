package com.project1hour.api.core.application.image.imports;

import java.io.InputStream;
import lombok.Builder;

public interface ImageExpressionManager {

    InputStream optimizeImage(ImageEditOptions options);

    String getImageExtension();

    @Builder
    record ImageEditOptions(
            int width,
            int height,
            int rotation,
            InputStream imageInput
    ) {
    }
}
