package com.project1hour.api.core.application.image.imports;

import java.io.InputStream;

public interface ImageClient {
    String uploadImage(InputStream image, String imageExtension);
}
