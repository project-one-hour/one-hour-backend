package com.project1hour.api.core.application.user;

import com.project1hour.api.core.domain.user.event.ProfileImageConfiguredEvent;
import com.project1hour.api.global.support.IdGenerator;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileImageConfigurationUseCase {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Command : 프로필 사진 설정 <br/>
     */
    protected List<Long> configureProfileImages(final List<InputStream> profileImageFiles) {

        List<Long> generatedImageIds = Stream.generate(IdGenerator::generateId)
                .limit(profileImageFiles.size())
                .toList();

        eventPublisher.publishEvent(new ProfileImageConfiguredEvent(profileImageFiles, generatedImageIds));
        return generatedImageIds;
    }
}
