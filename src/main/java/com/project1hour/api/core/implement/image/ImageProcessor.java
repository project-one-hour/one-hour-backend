package com.project1hour.api.core.implement.image;

import com.project1hour.api.core.domain.image.ImageFile;
import com.project1hour.api.core.domain.image.ImageUploader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ImageProcessor {

    private final ImageUploader imageUploader;

    public List<String> uploadImages(final List<MultipartFile> images) {
        return images.stream()
                .map(ImageFile::from)
                .map(imageUploader::upload)
                .toList();
    }
}
