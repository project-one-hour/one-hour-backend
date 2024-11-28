package com.project1hour.api.core.infrastructure.persistence;

import com.project1hour.api.core.application.user.model.TokenPackage;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(
        name = "oauth_info",
        uniqueConstraints = @UniqueConstraint(columnNames = {"provider_type", "user_social_id"})
)
@SQLDelete(sql = "UPDATE oauth_info SET deleted_at = now() WHERE oauth_info_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class OauthInfoEntity extends BaseEntity {

    @Id
    @Tsid
    @Column(name = "oauth_info_id")
    @EqualsAndHashCode.Include
    private Long id;

    @With
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(nullable = false)
    private String userSocialId;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "access_token_expires_in")
    private Integer accessTokenExpiresIn;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "refresh_token_expires_in")
    private Integer refreshTokenExpiresIn;

    public static OauthInfoEntity createOauthInfo(final String provider, final String userSocialId,
                                                  final TokenPackage tokenPackage) {
        return OauthInfoEntity.builder()
                .provider(provider)
                .userSocialId(userSocialId)
                .accessToken(tokenPackage.accessToken())
                .accessTokenExpiresIn(tokenPackage.accessTokenExpiresIn())
                .refreshToken(tokenPackage.refreshToken())
                .refreshTokenExpiresIn(tokenPackage.refreshTokenExpiresIn())
                .build();
    }

    public OauthInfoEntity updateOauthInfo(final TokenPackage tokenPackage) {
        return toBuilder()
                .accessToken(tokenPackage.accessToken())
                .accessTokenExpiresIn(tokenPackage.accessTokenExpiresIn())
                .refreshToken(tokenPackage.refreshToken())
                .refreshTokenExpiresIn(tokenPackage.refreshTokenExpiresIn())
                .build();
    }
}
