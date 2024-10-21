package com.project1hour.api.core.infrastructure.image.manager;

import com.drew.imaging.FileType;
import com.drew.imaging.FileTypeDetector;
import com.project1hour.api.core.application.image.imports.ImageValidationManager;
import com.project1hour.api.global.support.IOUtils;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Set;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultImageValidationManager implements ImageValidationManager {

    private final Set<String> supportedImageExtensions;
    private final int imageSizeLimit;

    public DefaultImageValidationManager(@Value("${image.formats}") final Set<String> supportedImageExtensions,
                                         @Value("${image.max-upload-size}") final int imageSizeLimit) {
        this.supportedImageExtensions = supportedImageExtensions;
        this.imageSizeLimit = imageSizeLimit * 1024 * 1024;
    }

    @Override
    public boolean validateImageSize(final long imageSize) {
        return imageSize < imageSizeLimit;
    }

    @Override
    public boolean validateImageFormat(final InputStream inputStream) {
        try (BufferedInputStream markSupportedInputStream = IOUtils.buffer(inputStream)) {
            FileType fileType = FileTypeDetector.detectFileType(markSupportedInputStream);

            return ObjectUtils.isNotEmpty(fileType.getAllExtensions()) &&
                    Arrays.stream(fileType.getAllExtensions()).anyMatch(supportedImageExtensions::contains);
        } catch (IOException e) {
            return false;
        }
    }
}
