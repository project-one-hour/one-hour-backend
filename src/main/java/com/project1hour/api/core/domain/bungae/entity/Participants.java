package com.project1hour.api.core.domain.bungae.entity;

import com.project1hour.api.core.domain.user.value.UserId;
import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import io.jsonwebtoken.lang.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participants {

    private static final int MAX_PARTICIPANTS_PER_BUNGAE = 6;
    protected static final int MIN_PARTICIPANTS_PER_BUNGAE = 1;

    // TODO : 구조 변경
    private int maxParticipantCount;

    private List<Participant> participantList;

    @Builder(toBuilder = true)
    public Participants(final int maxParticipantCount,
                        @Singular("participant") final List<Participant> participantList) {
        this.maxParticipantCount = maxParticipantCount;
        this.participantList = List.copyOf(participantList);
        validateParticipantLimit();
        validateParticipantListCount();
    }

    private void validateParticipantLimit() {
        if (maxParticipantCount < MIN_PARTICIPANTS_PER_BUNGAE) {
            String message = String.format("번개 모집 인원 수가 1명 미만일 수 없습니다. 모집 인원 수 = %d", maxParticipantCount);
            throw new BadRequestException(message, ErrorCode.INVALID_PARTICIPANTS_COUNT_MIN);
        }

        if (maxParticipantCount > MAX_PARTICIPANTS_PER_BUNGAE) {
            String message = String.format("번개 모집 인원 수가 6명을 초과할 수 없습니다. 모집 인원 수 = %d", maxParticipantCount);
            throw new BadRequestException(message, ErrorCode.INVALID_PARTICIPANTS_COUNT_MAX);
        }
    }

    private void validateParticipantListCount() {
        if (Collections.isEmpty(participantList)) {
            throw new BadRequestException("번개 참가 인원이 없습니다.", ErrorCode.EMPTY_BUNGAE_PARTICIPANT_LIST);
        }

        if (participantList.size() > MAX_PARTICIPANTS_PER_BUNGAE) {
            String message = String.format("번개 참가 인원 수를 초과했습니다. 최대 모집 인원 수 = %d", maxParticipantCount);
            throw new BadRequestException(message, ErrorCode.EXCEEDED_BUNGAE_PARTICIPANT_LIST_SIZE);
        }
    }


    public static Participants createNewParticipants(final UserId userId, final Bungae bungae,
                                                     final int maxParticipants) {
        return Participants.builder()
                .maxParticipantCount(maxParticipants)
                .participant(Participant.createHostParticipant(userId, bungae))
                .build();
    }
}
