package com.project1hour.api.core.application.image.manager;

import java.io.InputStream;

public interface ImageValidationManager {

    boolean validateImage(ImageResource imageInput);

    record ImageResource(InputStream inputStream, long size) {
    }
}
