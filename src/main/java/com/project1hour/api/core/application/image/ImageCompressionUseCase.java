package com.project1hour.api.core.application.image;

import com.project1hour.api.core.application.image.exports.ImageCompressionFacade;
import com.project1hour.api.core.application.image.imports.ImageExpressionManager;
import com.project1hour.api.core.application.image.imports.ImageExpressionManager.ImageEditOptions;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageCompressionUseCase implements ImageCompressionFacade {

    public static final int DEFAULT_IMAGE_WIDTH = 0;
    public static final int DEFAULT_IMAGE_HEIGHT = 0;

    private final ImageValidationUseCase imageValidationUseCase;
    private final ImageRotationRetrieveUseCase imageRotationRetrieveUseCase;

    private final ImageExpressionManager imageExpressionManager;

    @Override
    public InputStream compressImage(final ImageFileItem imageFileItem) {
        if (imageValidationUseCase.validateImage(imageFileItem.originInputStream(), imageFileItem.originImageSize())) {
            int imageRotation = imageRotationRetrieveUseCase.getImageRotationInfo(imageFileItem.originInputStream());
            return compressImage(imageRotation, imageFileItem.originInputStream());
        }

        return imageFileItem.originInputStream();
    }

    protected InputStream compressImage(final int imageRotation, final InputStream imageInput) {
        ImageEditOptions options = ImageEditOptions.builder()
                .width(DEFAULT_IMAGE_WIDTH)
                .height(DEFAULT_IMAGE_HEIGHT)
                .rotation(imageRotation)
                .imageInput(imageInput)
                .build();

        return imageExpressionManager.optimizeImage(options);
    }
}
