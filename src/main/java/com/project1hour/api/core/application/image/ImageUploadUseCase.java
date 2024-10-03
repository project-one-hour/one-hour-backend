package com.project1hour.api.core.application.image;

import com.project1hour.api.core.application.image.api.ImageClient;
import com.project1hour.api.core.application.image.eventhandler.ImageUploadEventHandler;
import com.project1hour.api.core.domain.image.ImageRepository;
import com.project1hour.api.core.domain.image.entity.Image;
import com.project1hour.api.core.domain.image.value.ImageName;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageUploadUseCase implements ImageUploadEventHandler {

    private final ImageRepository imageRepository;
    private final ImageClient imageClient;

    @Override
    public void handleImageUpload(final PayLoad payLoad) {
        processImageUpload(payLoad.imageId(), payLoad.inputStream());
    }

    /**
     * Command : 이미지 업로드
     */
    protected void processImageUpload(final Long imageId, final InputStream image) {
        String imageName = imageClient.uploadImage(image);

        Image uploadedImage = Image.createImage()
                .id(imageId)
                .imageName(new ImageName(imageName))
                .build();
        imageRepository.save(uploadedImage);
    }

    void method() {

    }
}
