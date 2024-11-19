package com.project1hour.api.core.presentation.observer;

import com.project1hour.api.core.application.image.exports.ImageUploadEventHandler;
import com.project1hour.api.core.application.image.exports.ImageUploadEventHandler.Payload;
import com.project1hour.api.core.application.user.model.event.ProfileImageConfiguredEvents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ImageEventListener {

    private final ImageUploadEventHandler imageUploadEventHandler;


    /**
     * TODO : Transaction을 새로 생성하는 것에 대해서 다시 한번 검토가 필요함 (이벤트 발행하는 방법?)
     */
    @TransactionalEventListener(ProfileImageConfiguredEvents.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void uploadProfileImages(final ProfileImageConfiguredEvents events) {
        events.value().stream()
                .map(event -> new Payload(event.imageId(), event.imageInput()))
                .forEach(imageUploadEventHandler::handleImageUpload);
    }
}
