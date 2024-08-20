package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.core.domain.user.value.AuthInfo;
import com.project1hour.api.core.domain.user.value.AuthProvider;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Set;
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

    @Embedded
    private UserInterests userInterests;

    @Embedded
    private ProfileImages profileImages;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "auth_id")
    private Auth userAuth;

    /**
     * TODO: 추후 소속 번개에 대한 정의
     */
    @Builder(builderClassName = "CreateNewUserBuilder", builderMethodName = "createNewUser")
    public User(final Nickname nickname, final Gender gender, final Birthday birthday, final Mbti mbti,
                final ServiceConsent serviceConsent, final Set<Long> interestIds,
                final String provider, final AuthInfo authInfo,
                final List<Long> imageIds, final int primaryImageIndex) {
        this.nickName = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.mbti = mbti;
        this.serviceConsent = serviceConsent;
        this.userInterests = addUserInterests(interestIds);
        this.profileImages = addProfileImages(imageIds, primaryImageIndex);
        this.userAuth = addUserAuth(provider, authInfo);
    }

    private UserInterests addUserInterests(final Set<Long> interestIds) {
        return UserInterests.builder()
                .interestIds(interestIds)
                .user(this)
                .build();
    }

    private ProfileImages addProfileImages(final List<Long> imageIds, final int primaryImageIndex) {
        return ProfileImages.builder()
                .imageIds(imageIds)
                .primaryImageIndex(primaryImageIndex)
                .user(this)
                .build();
    }

    private Auth addUserAuth(final String provider, final AuthInfo authInfo) {
        return Auth.builder()
                .provider(AuthProvider.find(provider))
                .authInfo(authInfo)
                .user(this)
                .build();
    }
}
