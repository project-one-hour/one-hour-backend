package com.project1hour.api.core.application.bungae;

import com.project1hour.api.core.application.bungae.exports.BungaeMeetCreationService;
import com.project1hour.api.core.domain.bungae.entity.Bungae;
import com.project1hour.api.core.domain.bungae.value.AgeRange;
import com.project1hour.api.core.domain.bungae.value.BungaeStatus;
import com.project1hour.api.core.domain.bungae.value.BungaeType;
import com.project1hour.api.core.domain.bungae.value.GenderType;
import com.project1hour.api.core.domain.bungae.value.RequiredMannerUser;
import com.project1hour.api.core.domain.bungae.value.Title;
import com.project1hour.api.core.domain.image.value.ImageId;
import com.project1hour.api.core.domain.user.value.UserId;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BungaeCreationUseCase implements BungaeMeetCreationService {

    private final CoverImageConfigurationUseCase coverImageConfigurationUseCase;
    private final LocationConfigurationUseCase locationConfigurationUseCase;

    private final ApplicationEventPublisher eventPublisher;


    @Override
    public void createMeetingBungae(Request request) {
        // 번개 커버 이미지 등록 유스케이스
        // ㄴ 번개 이미지 지정 -> 회원가입과 비슷하게
        // ㄴ 번개 이미지 미지정 -> 만남 : 카테고리 기본 이미지
        //                  -> 채팅 : 유저 랜덤 프로필 이미지

        // 번개 생성
        createBungae(
                1L,
                request.userId(),
                request.title(),
                request.bungaeDescription(),
                request.ageLowerLimit(),
                request.ageUpperLimit(),
                request.genderType(),
                request.requiredMannerUser(),
                BungaeType.MEET,
                request.categoryId(),
                request.locationDescription(),
                request.maxParticipants(),
                request.startAt()
        );
        // 번개 장소 지정
    }

    // TODO
    protected Bungae createBungae(final Long imageId, final Long userId, final String title,
                                  final String bungaeDescription, final int ageLowerLimit, final int ageUpperLimit,
                                  final String genderType, final boolean requiredMannerUser,
                                  final BungaeType bungaeType, final Long categoryId, final String locationDescription,
                                  final int maxParticipants, final LocalDateTime startAt) {
        Bungae newBungae = Bungae.builder()
                .imageId(new ImageId(imageId))
                .title(new Title(title))
                .bungaeDescription(bungaeDescription)
                .ageRange(new AgeRange(ageLowerLimit, ageUpperLimit))
                .genderType(GenderType.find(genderType))
                .requiredMannerUser(new RequiredMannerUser(requiredMannerUser))
                .bungaeType(bungaeType)
                .bungaeStatus(BungaeStatus.PENDING)
//                .category(Category.findById(categoryId))
                .locationDescription(locationDescription)
                .startAt(startAt)
                .buildNewBungae(new UserId(userId), maxParticipants);

        return null;
    }
}
