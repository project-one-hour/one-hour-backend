package com.project1hour.api.core.application.image.manager;

import java.io.InputStream;

public interface ImageFileValidationManager {

    boolean validateImageIntegrity(InputStream image);

    boolean validateCompression(InputStream image, double maxCompressionRatio);

    boolean validateAspectRatio(InputStream image);
}
