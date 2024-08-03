package com.project1hour.api.core.domain.user;

import com.project1hour.api.core.domain.user.entity.Auth;
import com.project1hour.api.core.domain.user.entity.ProfileImage;
import com.project1hour.api.core.domain.user.entity.UserInterest;
import com.project1hour.api.core.domain.user.value.Birthday;
import com.project1hour.api.core.domain.user.value.Gender;
import com.project1hour.api.core.domain.user.value.Mbti;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.core.domain.user.value.ServiceConsent;
import com.project1hour.api.global.domain.AbstractEntity;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "app_user")
@SQLDelete(sql = "UPDATE user SET deleted_at = now() WHERE user_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractEntity<Long> {

    @Id
    @Tsid
    @Column(name = "user_id")
    private Long id;

    @Embedded
    private Nickname nickName;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, updatable = false)
    private Gender gender;

    @Embedded
    private Birthday birthday;

    @Embedded
    private Mbti mbti;

    @Embedded
    private ServiceConsent serviceConsent;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserInterest> userInterests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileImage> profileImages;

    @OneToOne
    @JoinColumn(name = "auth_id")
    private Auth userAuth;

    /**
     * TODO: 추후 소속 번개에 대한 정의
     */
    @Builder
    public User(final Long id, final Nickname nickname, final Gender gender, final Birthday birthday, final Mbti mbti,
                final ServiceConsent serviceConsent, final List<UserInterest> userInterests,
                final List<ProfileImage> profileImages, final Auth userAuth) {
        this.id = id;
        this.nickName = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.mbti = mbti;
        this.serviceConsent = serviceConsent;
        this.userInterests = userInterests; // TODO: 5개
        this.profileImages = profileImages; // TODO: 3개
        this.userAuth = userAuth;
    }
}
