package com.project1hour.api.core.application.image.exports;

import com.project1hour.api.core.domain.image.value.ImageId;
import java.io.InputStream;

public interface ImageUploadEventHandler {

    void handleImageUpload(Payload payLoad);

    record Payload(ImageId imageId, InputStream inputStream) {
    }
}
