package com.project1hour.api.core.application.bungae;

import com.project1hour.api.core.application.bungae.model.event.CoverImageConfiguredEvent;
import com.project1hour.api.global.support.IdGenerator;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class CoverImageConfigurationUseCase {

    private final ApplicationEventPublisher eventPublisher;

    protected Long configureCoverImage(final InputStream imageInput) {
        Long coverImageId = IdGenerator.generateId();
        eventPublisher.publishEvent(new CoverImageConfiguredEvent(coverImageId, imageInput));
        return coverImageId;
    }
}
