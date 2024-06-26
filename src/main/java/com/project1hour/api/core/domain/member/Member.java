package com.project1hour.api.core.domain.member;

import com.project1hour.api.core.domain.member.profileinfo.Birthday;
import com.project1hour.api.core.domain.member.profileinfo.Gender;
import com.project1hour.api.core.domain.member.profileinfo.Mbti;
import com.project1hour.api.core.domain.member.profileinfo.Nickname;
import com.project1hour.api.core.domain.member.profileinfo.ProfileImage;
import com.project1hour.api.global.domain.CommonField;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE member SET deleted_at = now() WHERE member_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
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
    @Column(nullable = false)
    private SignUpStatus signUpStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private MarketingInfoStatus marketingInfoStatus;

    @Embedded
    private ProfileImage profileImage;

    @Embedded
    private CommonField commonField = new CommonField();

    @Builder
    public Member(final Long id, final Nickname nickname, final Gender gender, final Birthday birthday, final Mbti mbti,
                  final SignUpStatus signUpStatus, final Authority authority, final ProfileImage profileImage,
                  final MarketingInfoStatus marketingInfoStatus) {
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.mbti = mbti;
        this.signUpStatus = signUpStatus;
        this.authority = authority;
        this.profileImage = profileImage;
        this.marketingInfoStatus = marketingInfoStatus;
    }

    public static Member createJustAuthenticatedMember() {
        return Member.builder()
                .authority(Authority.MEMBER)
                .signUpStatus(SignUpStatus.AUTHENTICATED)
                .build();
    }

    public Member signUp(final String nickname, final String gender, final LocalDate birthday, final String mbti,
                         final boolean allowingMarketingInfo) {
        return Member.builder()
                .id(id)
                .nickname(new Nickname(nickname))
                .gender(Gender.find(gender))
                .birthday(new Birthday(birthday))
                .mbti(Mbti.find(mbti))
                .signUpStatus(SignUpStatus.SIGNED_UP)
                .authority(authority)
                .marketingInfoStatus(MarketingInfoStatus.selectStatus(allowingMarketingInfo))
                .build();
    }

    public Member addNewProfileImages(final List<String> imageUrls) {
        return Member.builder()
                .id(id)
                .nickname(nickname)
                .gender(gender)
                .birthday(birthday)
                .mbti(mbti)
                .signUpStatus(signUpStatus)
                .authority(authority)
                .profileImage(ProfileImage.from(imageUrls))
                .marketingInfoStatus(marketingInfoStatus)
                .build();
    }

    public boolean isAlreadySignedUp() {
        return signUpStatus == SignUpStatus.SIGNED_UP;
    }

    public boolean hasAnyProfileImage() {
        return profileImage != null;
    }
}
