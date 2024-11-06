package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.core.domain.user.value.Birthday;
import com.project1hour.api.core.domain.user.value.Gender;
import com.project1hour.api.core.domain.user.value.MarketingConsent;
import com.project1hour.api.core.domain.user.value.Mbti;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.core.domain.user.value.NotificationConsent;
import com.project1hour.api.core.domain.user.value.ProfileImageType;
import com.project1hour.api.core.domain.user.value.SignUpStatus;
import com.project1hour.api.global.entity.AbstractEntity;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    @Enumerated(EnumType.STRING)
    private MarketingConsent marketingConsent;

    @Enumerated(EnumType.STRING)
    private NotificationConsent notificationConsent;

    @Embedded
    private UserInterests userInterests;

    @Embedded
    private ProfileImages profileImages;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SignUpStatus signUpStatus;

    @Builder(toBuilder = true)
    public User(final Nickname nickname, final Gender gender, final Birthday birthday, final Mbti mbti,
                final MarketingConsent marketingConsent, final NotificationConsent notificationConsent,
                @ObtainVia(method = "userInterestsToList") final List<UserInterest> userInterests,
                @ObtainVia(method = "profileImagesToList") final List<ProfileImage> profileImages,
                final SignUpStatus signUpStatus) {
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.mbti = mbti;
        this.marketingConsent = marketingConsent;
        this.notificationConsent = notificationConsent;
        this.userInterests = createUserInterests(userInterests);
        this.profileImages = createProfileImages(profileImages);
        this.signUpStatus = signUpStatus;
    }

    public static User createPendingUser() {
        User user = new User();
        user.signUpStatus = SignUpStatus.AUTHENTICATED;
        return user;
    }

    public boolean isProfileRequired() {
        return signUpStatus == SignUpStatus.AUTHENTICATED;
    }

    private ProfileImages createProfileImages(final List<ProfileImage> profileImages) {
        if (profileImages == null) {
            return null;
        }

        return profileImages.stream()
                .map(profileImage -> profileImage.toBuilder().user(this).build())
                .collect(Collectors.collectingAndThen(Collectors.toList(), ProfileImages::new));
    }

    private List<ProfileImage> profileImagesToList() {
        return Optional.ofNullable(profileImages)
                .map(ProfileImages::getProfileImageList)
                .orElseGet(Collections::emptyList);
    }

    private UserInterests createUserInterests(final List<UserInterest> userInterests) {
        if (userInterests == null) {
            return null;
        }

        return userInterests.stream()
                .map(userInterest -> userInterest.toBuilder().user(this).build())
                .collect(Collectors.collectingAndThen(Collectors.toList(), UserInterests::new));
    }

    private List<UserInterest> userInterestsToList() {
        return Optional.ofNullable(userInterests)
                .map(UserInterests::getUserInterestList)
                .orElseGet(Collections::emptyList);
    }

    public static class UserBuilder {

        public UserBuilder userInterest(final Long interestId) {
            if (this.userInterests == null) {
                this.userInterests = new ArrayList<>();
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
