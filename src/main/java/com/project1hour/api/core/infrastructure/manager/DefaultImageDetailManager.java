package com.project1hour.api.core.infrastructure.manager;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.project1hour.api.core.application.image.imports.ImageDetailManager;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultImageDetailManager implements ImageDetailManager {

    @Override
    public int getImageRotation(final InputStream imageInput) {
        try (imageInput) {
            Directory directory = ImageMetadataReader.readMetadata(imageInput)
                    .getFirstDirectoryOfType(ExifIFD0Directory.class);

            if (directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
                return directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
            }

            return DEFAULT_IMAGE_ROTATION;
        } catch (Exception e) {
            log.error("이미지 회전 정보를 알 수 없음 : {}", ExceptionUtils.getStackTrace(e));
            return DEFAULT_IMAGE_ROTATION;
        }
    }
}
