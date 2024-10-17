package com.project1hour.api.core.domain.bungae.entity;

import com.project1hour.api.core.domain.bungae.value.BungaeRole;
import com.project1hour.api.global.domain.AbstractEntity;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@SQLDelete(sql = "UPDATE user SET deleted_at = now() WHERE bungae_id = ?")
//@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participant extends AbstractEntity<Long> {

    @Id
    @Tsid
    @Column(name = "participant_id")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bungae_id")
    private Bungae bungae;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private BungaeRole role;
}
