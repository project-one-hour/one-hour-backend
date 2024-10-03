package com.project1hour.api.core.infrastructure.image.manager;

import com.drew.imaging.FileType;
import com.drew.imaging.FileTypeDetector;
import com.project1hour.api.core.application.image.manager.ImageValidationManager;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StandardImageValidationManager implements ImageValidationManager {

    private final Set<String> supportedImageExtensions;
    private final int imageSizeLimit;

    public StandardImageValidationManager(@Value("${image.formats}") Set<String> supportedImageExtensions,
                                          @Value("${image.max-upload-size}") final int imageSizeLimit) {
        this.supportedImageExtensions = supportedImageExtensions;
        this.imageSizeLimit = imageSizeLimit;
    }

    @Override
    public boolean validateImage(final ImageResource imageResource) {
        InputStream imageInputStream = imageResource.inputStream();
        long imageSize = imageResource.size();
        return validateImageFormat(imageInputStream) && validateImageFileSize(imageSize);
    }

    public boolean validateImageFileSize(final long imageSize) {
        return imageSize < imageSizeLimit;
    }

    public boolean validateImageFormat(final InputStream inputStream) {
        try {
            BufferedInputStream bufferedInputStream = IOUtils.buffer(inputStream);
            FileType fileType = FileTypeDetector.detectFileType(bufferedInputStream);

            return Arrays.stream(fileType.getAllExtensions()).anyMatch(supportedImageExtensions::contains);
        } catch (IOException e) {
            return false;
        }
    }
}