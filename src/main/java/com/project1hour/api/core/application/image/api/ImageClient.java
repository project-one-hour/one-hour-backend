package com.project1hour.api.core.application.image.api;

import java.io.InputStream;

public interface ImageClient {
    String uploadImage(InputStream image, String imageExtension);
}
