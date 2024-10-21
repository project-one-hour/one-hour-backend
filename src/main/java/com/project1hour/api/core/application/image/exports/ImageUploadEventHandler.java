package com.project1hour.api.core.application.image.exports;

import java.io.InputStream;

public interface ImageUploadEventHandler {

    void handleImageUpload(PayLoad payLoad);

    record PayLoad(Long imageId, InputStream inputStream) {
    }
}
