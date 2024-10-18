package com.project1hour.api.core.domain.bungae.entity;

import com.project1hour.api.core.domain.bungae.value.BungaeCondition;
import com.project1hour.api.core.domain.bungae.value.BungaeStatus;
import com.project1hour.api.core.domain.bungae.value.BungaeType;
import com.project1hour.api.core.domain.bungae.value.Description;
import com.project1hour.api.core.domain.bungae.value.Location;
import com.project1hour.api.core.domain.bungae.value.Title;
import com.project1hour.api.global.domain.AbstractEntity;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE user SET deleted_at = now() WHERE bungae_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bungae extends AbstractEntity<Long> {

    @Id
    @Tsid
    @Column(name = "bungae_id")
    private Long id;

    @Column(nullable = false)
    private Long imageId;

    @Embedded
    private BungaeCondition condition;

    @Embedded
    private Participants participants;

    @Embedded
    private Title title;

    @Embedded
    private Description description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime startAt;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private BungaeType bungaeType;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private BungaeStatus bungaeStatus;

    @Embedded
    private Location location;
}
