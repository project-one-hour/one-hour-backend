package com.project1hour.api.core.domain.user.entity;

import static com.project1hour.api.core.domain.user.value.ProfileImageType.PRIMARY;
import static com.project1hour.api.core.domain.user.value.ProfileImageType.SECONDARY;

import com.project1hour.api.core.domain.user.value.Birthday;
import com.project1hour.api.core.domain.user.value.Gender;
import com.project1hour.api.core.domain.user.value.MarketingConsent;
import com.project1hour.api.core.domain.user.value.Mbti;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.core.domain.user.value.NotificationConsent;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
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
@SQLDelete(sql = "UPDATE app_user SET deleted_at = now() WHERE user_id = ?")
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
    @Column(length = 10)
    private Gender gender;

    @Embedded
    private Birthday birthday;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
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
    public User(final Long id, final Nickname nickname, final Gender gender, final Birthday birthday, final Mbti mbti,
                final MarketingConsent marketingConsent, final NotificationConsent notificationConsent,
                final SignUpStatus signUpStatus, final LocalDateTime createdAt, final LocalDateTime updatedAt,
                @ObtainVia(method = "userInterestsToList") final List<UserInterest> userInterests,
                @ObtainVia(method = "profileImagesToList") final List<ProfileImage> profileImages) {
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.mbti = mbti;
        this.marketingConsent = marketingConsent;
        this.notificationConsent = notificationConsent;
        this.signUpStatus = signUpStatus;
        super.createdAt = createdAt;
        super.updatedAt = updatedAt;
        this.userInterests = createUserInterests(userInterests);
        this.profileImages = createProfileImages(profileImages);
    }

    public static User createPendingUser() {
        User user = new User();
        user.signUpStatus = SignUpStatus.AUTHENTICATED;
        return user;
    }

    public boolean isProfileRequired() {
        return SignUpStatus.AUTHENTICATED.equals(signUpStatus);
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
                .map(profileImages -> new ArrayList<>(profileImages.getProfileImageList()))
                .orElseGet(ArrayList::new);
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
                .map(userInterests -> new ArrayList<>(userInterests.getUserInterestList()))
                .orElseGet(ArrayList::new);
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
                    .profileImageType(isPrimaryImage ? PRIMARY : SECONDARY)
                    .build();
            this.profileImages.add(profileImage);
            return this;
        }
    }
}
