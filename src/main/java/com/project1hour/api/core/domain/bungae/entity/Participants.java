package com.project1hour.api.core.domain.bungae.entity;

import static com.project1hour.api.core.domain.bungae.value.Capacity.MAX_CAPACITY_PER_BUNGAE;

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

    private List<Participant> participantList;

    @Builder(toBuilder = true)
    public Participants(@Singular("participant") final List<Participant> participantList) {
        this.participantList = List.copyOf(participantList);
        validateParticipantListCount();
    }

    private void validateParticipantListCount() {
        if (Collections.isEmpty(participantList)) {
            throw new BadRequestException("번개 참가 인원이 없습니다.", ErrorCode.EMPTY_BUNGAE_PARTICIPANT_LIST);
        }

        if (participantList.size() > MAX_CAPACITY_PER_BUNGAE) {
            String message = String.format("번개 참가 인원 수를 초과했습니다. 최대 모집 인원 수 = %d", MAX_CAPACITY_PER_BUNGAE);
            throw new BadRequestException(message, ErrorCode.EXCEEDED_BUNGAE_PARTICIPANT_LIST_SIZE);
        }
    }
}
