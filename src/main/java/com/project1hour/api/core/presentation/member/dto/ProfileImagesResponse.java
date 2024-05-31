package com.project1hour.api.core.presentation.member.dto;

import java.util.List;

public record ProfileImagesResponse(
        List<String> imageUrls
) {
}
