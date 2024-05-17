package com.project1hour.api.core.domain.member;

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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, length = 10)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Mbti mbti;

    @Enumerated(EnumType.STRING)
    private SignUpStatus signUpStatus;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private MarketingInfoStatus marketingInfoStatus;

    @Embedded
    private CommonField commonField = new CommonField();

    @Builder
    public Member(final Long id, final String nickname, final Gender gender, final LocalDate birthday, final Mbti mbti,
                  final SignUpStatus signUpStatus) {
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.mbti = mbti;
        this.signUpStatus = signUpStatus;
    }

    public static Member createJustAuthenticatedMember() {
        return Member.builder()
                .signUpStatus(SignUpStatus.AUTHENTICATED)
                .build();
    }
}
