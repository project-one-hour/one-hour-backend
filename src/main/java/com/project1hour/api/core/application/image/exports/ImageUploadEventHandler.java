package com.project1hour.api.core.application.image.exports;

import java.io.InputStream;

public interface ImageUploadEventHandler {

    void handleImageUpload(Payload payLoad);

    record Payload(Long imageId, InputStream inputStream) {
    }
}
