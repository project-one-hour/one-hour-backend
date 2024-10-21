package com.project1hour.api.core.application.image.exports;

import java.io.InputStream;

public interface ImageCompressionFacade {

    InputStream compressImage(ImageFileItem imageFileItem);

    /**
     * FileItem의 InputStream을 반환해야 합니다. 자세한 내용 -> 010-5912-8050
     */
    interface ImageFileItem {
        InputStream originInputStream();

        long originImageSize();
    }
}
