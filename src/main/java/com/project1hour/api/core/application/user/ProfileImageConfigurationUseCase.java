package com.project1hour.api.core.application.user;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import com.project1hour.api.core.application.user.model.ProfileImageInfo;
import com.project1hour.api.core.application.user.model.ProfileImageInput;
import com.project1hour.api.core.application.user.model.event.ProfileImageConfiguredEvent;
import com.project1hour.api.global.support.IdGenerator;
import java.io.InputStream;
import java.util.List;
import java.util.stream.IntStream;
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
    protected List<ProfileImageInfo> configureProfileImages(final List<ProfileImageInput> profileImageInputs) {
        List<Long> generatedImageIds = Stream.generate(IdGenerator::generateId)
                .limit(profileImageInputs.size())
                .toList();

        List<ProfileImageInfo> profileImageInfos = IntStream.range(INTEGER_ZERO, profileImageInputs.size())
                .mapToObj(i -> new ProfileImageInfo(generatedImageIds.get(i), profileImageInputs.get(i).isPrimary()))
                .toList();

        List<InputStream> imageInputs = profileImageInputs.stream()
                .map(ProfileImageInput::inputStream)
                .toList();

        eventPublisher.publishEvent(new ProfileImageConfiguredEvent(imageInputs, generatedImageIds));
        return profileImageInfos;
    }
}
