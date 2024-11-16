package com.project1hour.api.core.presentation.observer;

import com.project1hour.api.core.application.image.exports.ImageUploadEventHandler;
import com.project1hour.api.core.application.user.model.event.ProfileImageConfiguredEvents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ImageEventListener {

    private final ImageUploadEventHandler imageUploadEventHandler;

    @TransactionalEventListener(ProfileImageConfiguredEvents.class)
    public void uploadProfileImage(final ProfileImageConfiguredEvents events) {
        events.value().stream()
                .map(event -> new ImageUploadEventHandler.PayLoad(event.imageId(), event.imageInput()))
                .forEach(imageUploadEventHandler::handleImageUpload);
    }
}
