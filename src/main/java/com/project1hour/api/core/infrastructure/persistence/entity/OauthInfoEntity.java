package com.project1hour.api.core.infrastructure.persistence.entity;

import com.project1hour.api.core.domain.user.entity.User;
import com.project1hour.api.global.entity.AbstractEntity;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(
        name = "oauth_info",
        uniqueConstraints = @UniqueConstraint(columnNames = {"provider_type", "user_social_id"})
)
@SQLDelete(sql = "UPDATE oauth_info SET deleted_at = now() WHERE oauth_info_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthInfoEntity extends AbstractEntity<Long> {

    @Id
    @Tsid
    @Column(name = "oauth_info_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProviderType providerType;

    @Column(nullable = false)
    private String userSocialId;

    private String accessToken;

    private int accessTokenExpiresIn;

    private String refreshToken;

    private int refreshTokenExpiresIn;

    @Builder(toBuilder = true)
    public OauthInfoEntity(final Long id, final User user, final ProviderType providerType,
                           final String accessToken, final String refreshToken, final String userSocialId,
                           final int accessTokenExpiresIn, final int refreshTokenExpiresIn,
                           final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.providerType = providerType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userSocialId = userSocialId;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        super.createdAt = createdAt;
        super.updatedAt = updatedAt;
    }
}
