package com.project1hour.api.core.presentation.observer;

import com.project1hour.api.core.application.image.exports.ImageUploadEventHandler;
import com.project1hour.api.core.application.image.exports.ImageUploadEventHandler.Payload;
import com.project1hour.api.core.application.user.model.event.ProfileImageConfiguredEvents;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageEventListener {

    private final ImageUploadEventHandler imageUploadEventHandler;

    @EventListener(ProfileImageConfiguredEvents.class)
    public void uploadProfileImages(final ProfileImageConfiguredEvents events) {
        events.value().stream()
                .map(event -> new Payload(event.imageId(), event.imageInput()))
                .forEach(imageUploadEventHandler::handleImageUpload);
    }
}
