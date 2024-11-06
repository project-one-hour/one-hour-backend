package com.project1hour.api.core.application.image.imports;

import java.io.InputStream;

public interface ImageValidationManager {

    boolean validateImageSize(long imageSize);

    boolean validateImageFormat(InputStream inputStream);
}
