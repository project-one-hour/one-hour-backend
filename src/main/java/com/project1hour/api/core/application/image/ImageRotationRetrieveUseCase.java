package com.project1hour.api.core.application.image;

import com.project1hour.api.core.application.image.imports.ImageDetailManager;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageRotationRetrieveUseCase {

    private final ImageDetailManager imageDetailManager;

    protected int getImageRotationInfo(final InputStream imageInput) {
        return imageDetailManager.getImageRotation(imageInput);
    }
}
