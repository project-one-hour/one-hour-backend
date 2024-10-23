package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.core.domain.user.value.AuthInfo;
import com.project1hour.api.core.domain.user.value.AuthProvider;
import com.project1hour.api.core.domain.user.value.Birthday;
import com.project1hour.api.core.domain.user.value.Gender;
import com.project1hour.api.core.domain.user.value.Mbti;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.core.domain.user.value.ProfileImageType;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.ObtainVia;
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
    private Nickname nickname;

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

    @Builder(setterPrefix = "with", toBuilder = true)
    public User(final Nickname nickname, final Gender gender, final Birthday birthday, final Mbti mbti,
                final ServiceConsent serviceConsent, final Auth userAuth,
                @ObtainVia(method = "userInterestsToSet") final Set<UserInterest> userInterests,
                @ObtainVia(method = "profileImagesToList") final List<ProfileImage> profileImages) {
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.mbti = mbti;
        this.serviceConsent = serviceConsent;
        this.userAuth = userAuth;
        this.userInterests = createUserInterests(userInterests);
        this.profileImages = createProfileImages(profileImages);
    }

    private ProfileImages createProfileImages(final List<ProfileImage> profileImages) {
        List<ProfileImage> profileImageList = profileImages.stream()
                .map(profileImage -> profileImage.toBuilder().user(this).build())
                .toList();
        return new ProfileImages(profileImageList);
    }

    private List<ProfileImage> profileImagesToList() {
        return profileImages.getProfileImageList();
    }

    private UserInterests createUserInterests(final Set<UserInterest> userInterests) {
        Set<UserInterest> userInterestSet = userInterests.stream()
                .map(userInterest -> userInterest.toBuilder().user(this).build())
                .collect(Collectors.toSet());
        return new UserInterests(userInterestSet);
    }

    private Set<UserInterest> userInterestsToSet() {
        return userInterests.getUserInterestSet();
    }

    public static class UserBuilder {

        public UserBuilder serviceConsent(final boolean marketingConsentAllowed,
                                          final boolean notificationConsentAllowed) {
            this.serviceConsent = ServiceConsent.of()
                    .marketingConsentAllowed(marketingConsentAllowed)
                    .notificationConsentAllowed(notificationConsentAllowed)
                    .build();
            return this;
        }

        public UserBuilder userAuth(final String provider, final AuthInfo authInfo) {
            this.userAuth = Auth.builder()
                    .provider(AuthProvider.find(provider))
                    .authInfo(authInfo)
                    .build();
            return this;
        }

        public UserBuilder userInterest(final Long interestId) {
            if (this.userInterests == null) {
                this.userInterests = new HashSet<>();
            }

            UserInterest userInterest = UserInterest.builder()
                    .interestId(interestId)
                    .build();
            this.userInterests.add(userInterest);

            return this;
        }

        public UserBuilder profileImage(final Long imageId, final boolean isPrimaryImage) {
            if (this.profileImages == null) {
                this.profileImages = new ArrayList<>();
            }
            ProfileImage profileImage = ProfileImage.builder()
                    .imageId(imageId)
                    .profileImageType(isPrimaryImage ? ProfileImageType.PRIMARY : ProfileImageType.SECONDARY)
                    .build();
            this.profileImages.add(profileImage);

            return this;
        }
    }
}
