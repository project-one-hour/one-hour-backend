package com.project1hour.api.core.application.image.imports;

import java.io.InputStream;

public interface ImageDetailManager {

    int DEFAULT_IMAGE_ROTATION = 1;

    int getImageRotation(InputStream imageInput);
}
