package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.core.domain.user.value.ProfileImageType;
import com.project1hour.api.global.entity.AbstractEntity;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE profile_image SET deleted_at = now() WHERE profile_image_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends AbstractEntity<Long> {

    @Id
    @Tsid
    @Column(name = "profile_image_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ProfileImageType profileImageType;

    @Builder(access = AccessLevel.PACKAGE, toBuilder = true)
    public ProfileImage(final Long id, final Long imageId, final User user, final ProfileImageType profileImageType,
                        final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.imageId = imageId;
        this.user = user;
        this.profileImageType = profileImageType;
        super.createdAt = createdAt;
        super.updatedAt = updatedAt;
    }
}
