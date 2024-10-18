package com.project1hour.api.core.domain.bungae.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participants {

    @Column(nullable = false, updatable = false)
    private int maxParticipants;

    @OneToMany(mappedBy = "bungae", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participantList;
}
