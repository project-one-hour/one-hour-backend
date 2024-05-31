package com.project1hour.api.core.presentation.image.dto;

import java.util.List;

public record SavedImagesResponse(
        List<String> imageUrls
) {
}
