package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.core.domain.AbstractDomainEntity;
import com.project1hour.api.core.domain.image.value.ImageId;
import com.project1hour.api.core.domain.user.value.Birthday;
import com.project1hour.api.core.domain.user.value.Gender;
import com.project1hour.api.core.domain.user.value.InterestId;
import com.project1hour.api.core.domain.user.value.MarketingConsent;
import com.project1hour.api.core.domain.user.value.Mbti;
import com.project1hour.api.core.domain.user.value.Nickname;
import com.project1hour.api.core.domain.user.value.NotificationConsent;
import com.project1hour.api.core.domain.user.value.ProfileImageType;
import com.project1hour.api.core.domain.user.value.SignUpStatus;
import com.project1hour.api.core.domain.user.value.UserId;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class User extends AbstractDomainEntity<UserId> {

    private final UserId id;

    private Nickname nickname;

    private Gender gender;

    private Birthday birthday;

    private Mbti mbti;

    private MarketingConsent marketingConsent;

    private NotificationConsent notificationConsent;

    private SignUpStatus signUpStatus;

    private UserInterests userInterests;

    private ProfileImages profileImages;

    @Builder(toBuilder = true)
    public User(final UserId id, final Nickname nickname, final Gender gender, final Birthday birthday, final Mbti mbti,
                final MarketingConsent marketingConsent, final NotificationConsent notificationConsent,
                final SignUpStatus signUpStatus, final UserInterests userInterests, final ProfileImages profileImages) {
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.mbti = mbti;
        this.marketingConsent = marketingConsent;
        this.notificationConsent = notificationConsent;
        this.signUpStatus = signUpStatus;
        this.userInterests = userInterests;
        this.profileImages = profileImages;
    }

    public static User createAuthenticatedUser() {
        User user = new User();
        user.signUpStatus = SignUpStatus.AUTHENTICATED;
        return user;
    }

    public boolean isProfileRequired() {
        return signUpStatus.isAuthenticated();
    }

    public List<UserInterest> getUserInterestsToList() {
        return List.copyOf(userInterests.getUserInterestList());
    }

    public List<ProfileImage> getProfileImagesToList() {
        return List.copyOf(profileImages.getProfileImageList());
    }

    public static class UserBuilder {

        private ProfileImages.ProfileImagesBuilder profileImagesBuilder;
        private UserInterests.UserInterestsBuilder userInterestsBuilder;

        public UserBuilder userInterest(final InterestId interestId) {
            if (userInterestsBuilder == null) {
                userInterestsBuilder = Optional.ofNullable(userInterests)
                        .map(UserInterests::toBuilder)
                        .orElseGet(UserInterests::builder);
            }

            UserInterest userInterest = UserInterest.createNewUserInterest(interestId);
            userInterestsBuilder.userInterest(userInterest);
            return this;
        }

        public UserBuilder profileImage(final ImageId imageId, final boolean isPrimaryImage) {
            if (profileImagesBuilder == null) {
                profileImagesBuilder = Optional.ofNullable(profileImages)
                        .map(ProfileImages::toBuilder)
                        .orElseGet(ProfileImages::builder);
            }

            ProfileImageType profileImageType = isPrimaryImage ? ProfileImageType.PRIMARY : ProfileImageType.SECONDARY;
            ProfileImage profileImage = ProfileImage.createNewProfileImage(imageId, profileImageType);
            profileImagesBuilder.profileImage(profileImage);
            return this;
        }

        public UserBuilder profileImage(final boolean isPrimaryImage) {
            return profileImage(null, isPrimaryImage);
        }

        public User buildWithAggregation() {
            userInterests = Optional.ofNullable(userInterestsBuilder)
                    .map(UserInterests.UserInterestsBuilder::build)
                    .orElse(userInterests);
            profileImages = Optional.ofNullable(profileImagesBuilder)
                    .map(ProfileImages.ProfileImagesBuilder::build)
                    .orElse(profileImages);
            return build();
        }
    }
}
