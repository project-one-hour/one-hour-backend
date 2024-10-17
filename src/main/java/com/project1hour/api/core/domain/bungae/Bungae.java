package com.project1hour.api.core.domain.bungae;

import com.project1hour.api.core.domain.bungae.value.BungaeType;
import com.project1hour.api.core.domain.interest.Category;
import com.project1hour.api.core.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

//@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE bungae SET deleted_at = now() WHERE bungae_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bungae {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bungae_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private BungaeType bungaeType;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private GenderFilterType genderFilterType;

    @Column(nullable = false)
    private int startAge;

    @Column(nullable = false)
    private int endAge;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MannerLevel levelLowerBound;

    @Column(nullable = false, columnDefinition = "point")
    private Point location;

    @Lob
    @Column(columnDefinition = "text")
    private String locationName;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false, updatable = false)
    private int maxParticipants;

    @Column(nullable = false, updatable = false)
    private int startDelayMinutes;

    @Column(length = 40, nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "text")
    private String description;

    private String coverImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    private Member host;

    @Builder
    public Bungae(final Long id, final BungaeType bungaeType, final GenderFilterType genderFilterType,
                  final int startAge, final int endAge, final MannerLevel levelLowerBound, final Point location,
                  final String locationName, final Category category, final int maxParticipants,
                  final int startDelayMinutes, final String title, final String description,
                  final String coverImageUrl) {
        this.id = id;
        this.bungaeType = bungaeType;
        this.genderFilterType = genderFilterType;
        this.startAge = startAge;
        this.endAge = endAge;
        this.levelLowerBound = levelLowerBound;
        this.location = location;
        this.locationName = locationName;
        this.category = category;
        this.maxParticipants = maxParticipants;
        this.startDelayMinutes = startDelayMinutes;
        this.title = title;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
    }
}
