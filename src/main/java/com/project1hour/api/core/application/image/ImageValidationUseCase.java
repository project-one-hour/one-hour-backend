package com.project1hour.api.core.application.image;

import com.project1hour.api.core.application.image.imports.ImageValidationManager;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageValidationUseCase {

    private final ImageValidationManager imageValidationManager;

    protected Boolean validateImage(final InputStream imageInput, final long imageSize) {
        boolean isValidImageFormat = imageValidationManager.validateImageFormat(imageInput);
        boolean isWithinSizeLimit = imageValidationManager.validateImageSize(imageSize);

        if (isValidImageFormat && !isWithinSizeLimit) {
            String message = String.format("이미지가 제한 크기보다 큽니다. 이미지 크기 = %d byte ", imageSize);
            throw new BadRequestException(message, ErrorCode.IMAGE_TOO_LARGE);
        }

        return isValidImageFormat;
    }
}
