package com.project1hour.api.core.domain.image.value;

import java.util.UUID;

public record ImageName(String value) {

    public static ImageName generatedRandom(final String imageExtension) {
        String randomImageName = UUID.randomUUID().toString();
        String extension = imageExtension.startsWith(".") ? imageExtension : "." + imageExtension;
        return new ImageName(randomImageName + extension);
    }
}
