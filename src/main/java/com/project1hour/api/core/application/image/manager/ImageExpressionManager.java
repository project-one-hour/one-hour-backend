package com.project1hour.api.core.application.image.manager;

import java.io.InputStream;

public interface ImageExpressionManager {

    InputStream optimizeImage(ImageEditOptions options);

    record ImageEditOptions(int width, int height, int rotation, InputStream imageInput) {

        public static final int DEFAULT_WIDTH = 0;
        public static final int DEFAULT_HEIGHT = 0;

        public static ImageEditOptions withDefaults(final int rotation, final InputStream imageInput) {
            return new ImageEditOptions(DEFAULT_WIDTH, DEFAULT_HEIGHT, rotation, imageInput);
        }
    }
}
