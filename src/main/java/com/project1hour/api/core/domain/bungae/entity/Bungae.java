package com.project1hour.api.core.domain.bungae.entity;

import static com.project1hour.api.core.domain.bungae.entity.Participants.MIN_PARTICIPANTS_PER_BUNGAE;

import com.project1hour.api.core.domain.AbstractDomainEntity;
import com.project1hour.api.core.domain.bungae.value.AgeRange;
import com.project1hour.api.core.domain.bungae.value.BungaeId;
import com.project1hour.api.core.domain.bungae.value.BungaeStatus;
import com.project1hour.api.core.domain.bungae.value.BungaeType;
import com.project1hour.api.core.domain.bungae.value.GenderType;
import com.project1hour.api.core.domain.bungae.value.RequiredMannerUser;
import com.project1hour.api.core.domain.bungae.value.Title;
import com.project1hour.api.core.domain.image.value.ImageId;
import com.project1hour.api.core.domain.user.value.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Builder.ObtainVia;
import lombok.Getter;

@Getter
public class Bungae extends AbstractDomainEntity<BungaeId> {

    private BungaeId id;

    private ImageId imageId;

    private Title title;

    private String bungaeDescription;

    private AgeRange ageRange;

    private GenderType genderType;

    private RequiredMannerUser requiredMannerUser;

    private BungaeType bungaeType;

    private BungaeStatus bungaeStatus;

    private String locationDescription;

//    private Category category;

    @Embedded
    private Participants participants;

    @Column(nullable = false, updatable = false)
    private LocalDateTime startAt;

    @Builder(toBuilder = true)
    public Bungae(final BungaeId id, final ImageId imageId, final AgeRange ageRange, final GenderType genderType,
                  final Title title, final String bungaeDescription, final RequiredMannerUser requiredMannerUser,
                  final BungaeType bungaeType, final BungaeStatus bungaeStatus, final String locationDescription,
//                  final Category category,
                  final LocalDateTime startAt,
                  @ObtainVia(method = "getMaxParticipantsCount") final int maxParticipantCount,
                  @ObtainVia(method = "participantsToList") final List<Participant> participants) {
        this.id = id;
        this.imageId = imageId;
        this.title = title;
        this.bungaeDescription = bungaeDescription;
        this.ageRange = ageRange;
        this.genderType = genderType;
        this.requiredMannerUser = requiredMannerUser;
//        this.category = category;
        this.bungaeType = bungaeType;
        this.bungaeStatus = bungaeStatus;
        this.locationDescription = locationDescription;
        this.startAt = startAt;
        this.participants = new Participants(maxParticipantCount, participants);
    }

    public boolean isChatType() {
        return bungaeType.equals(BungaeType.CHAT);
    }

    /**
     * Participants가 null 이면 최소 모집 인원 수를 반환
     */
    private int getMaxParticipantsCount() {
        return participants == null ? MIN_PARTICIPANTS_PER_BUNGAE : participants.getMaxParticipantCount();
    }

    private List<Participant> participantsToList() {
        return Optional.ofNullable(participants)
                .map(participants -> new ArrayList<>(participants.getParticipantList()))
                .orElseGet(ArrayList::new);
    }

    public static class BungaeBuilder {

        // 수정 이거 안됨 딱봐다 먼저 값 객체 부터 작성
        public Bungae buildNewBungae(final UserId userId, final int maxParticipants) {
            Bungae newBungae = this.build();
            newBungae.participants = Participants.createNewParticipants(userId, newBungae, maxParticipants);
            return newBungae;
        }
    }
}
