package com.project1hour.api.core.domain.bungae.entity;

import com.project1hour.api.core.domain.AbstractDomainEntity;
import com.project1hour.api.core.domain.bungae.value.BungaeRole;
import com.project1hour.api.core.domain.bungae.value.ParticipantId;
import com.project1hour.api.core.domain.user.value.UserId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;


@Getter
public class Participant extends AbstractDomainEntity<ParticipantId> {

    private ParticipantId id;

    private UserId userId;

    private BungaeRole role;

    @Builder(access = AccessLevel.PROTECTED, toBuilder = true)
    public Participant(final ParticipantId id, final UserId userId, final BungaeRole role) {
        this.id = id;
        this.userId = userId;
        this.role = role;
    }

    public static Participant createHostParticipant(final UserId userId, final Bungae bungae) {
        return Participant.builder()
                .userId(userId)
                .role(BungaeRole.HOST)
                .build();
    }
}
