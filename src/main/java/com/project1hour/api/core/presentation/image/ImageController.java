package com.project1hour.api.core.presentation.image;

import com.project1hour.api.core.domain.image.ImageFile;
import com.project1hour.api.core.domain.image.ImageUploader;
import com.project1hour.api.core.presentation.image.dto.SavedImagesResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageUploader imageUploader;

    @PostMapping("/upload")
    public ResponseEntity<SavedImagesResponse> upload(@RequestPart final List<MultipartFile> images) {
        List<String> imageUrls = images.stream()
                .map(ImageFile::from)
                .map(imageUploader::upload)
                .toList();
        SavedImagesResponse response = new SavedImagesResponse(imageUrls);

        return ResponseEntity.ok(response);
    }
}
