package com.project1hour.api.core.application.image;

import com.project1hour.api.core.application.image.exports.ImageUploadEventHandler;
import com.project1hour.api.core.application.image.imports.ImageClient;
import com.project1hour.api.core.application.image.imports.ImageCommandPort;
import com.project1hour.api.core.application.image.imports.ImageExpressionManager;
import com.project1hour.api.core.domain.image.entity.Image;
import com.project1hour.api.core.domain.image.value.ImageId;
import com.project1hour.api.core.domain.image.value.ImageName;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageUploadUseCase implements ImageUploadEventHandler {

    private final ImageCommandPort imageCommandPort;
    private final ImageExpressionManager imageExpressionManager;
    private final ImageClient imageClient;

    @Override
    public void handleImageUpload(final Payload payLoad) {
        processImageUpload(payLoad.imageId(), payLoad.inputStream());
    }

    /**
     * Command : 이미지 업로드
     */
    protected void processImageUpload(final ImageId imageId, final InputStream imageInput) {
        String imageExtension = imageExpressionManager.getImageExtension();
        ImageName randomImageName = ImageName.generatedRandom(imageExtension);

        String imagePath = imageClient.uploadImage(imageInput, imageExtension);
        Image uploadedImage = new Image(imageId, imagePath, randomImageName);
        imageCommandPort.saveImage(uploadedImage);
    }
}
