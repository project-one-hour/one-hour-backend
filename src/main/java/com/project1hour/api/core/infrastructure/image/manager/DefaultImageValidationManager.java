package com.project1hour.api.core.infrastructure.image.manager;

import com.drew.imaging.FileType;
import com.drew.imaging.FileTypeDetector;
import com.project1hour.api.core.application.image.manager.ImageValidationManager;
import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.InfraStructureException;
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

    public DefaultImageValidationManager(@Value("${image.formats}") Set<String> supportedImageExtensions,
                                         @Value("${image.max-upload-size}") final int imageSizeLimit) {
        this.supportedImageExtensions = supportedImageExtensions;
        this.imageSizeLimit = imageSizeLimit * 1024 * 1024;
    }

    @Override
    public boolean validateImage(final ImageResource imageResource) {
        boolean isValidImageFormat = validateImageFormat(imageResource.inputStream());
        boolean isWithinSizeLimit = validateImageFileSize(imageResource.size());

        if (isValidImageFormat && !isWithinSizeLimit) {
            String message = String.format("이미지가 제한 크기보다 큽니다. 이미지 크기 = %d byte ", imageResource.size());
            throw new InfraStructureException(message, ErrorCode.IMAGE_TOO_LARGE);
        }

        return isValidImageFormat;
    }

    public boolean validateImageFileSize(final long imageSize) {
        return imageSize < imageSizeLimit;
    }

    public boolean validateImageFormat(final InputStream inputStream) {
        try {
            BufferedInputStream bufferedInputStream = IOUtils.buffer(inputStream);
            FileType fileType = FileTypeDetector.detectFileType(bufferedInputStream);

            return ObjectUtils.isNotEmpty(fileType.getAllExtensions()) &&
                    Arrays.stream(fileType.getAllExtensions()).anyMatch(supportedImageExtensions::contains);
        } catch (IOException e) {
            return false;
        }
    }
}