package com.project1hour.api.core.domain.member;

import com.project1hour.api.core.domain.interest.Interest;
import com.project1hour.api.global.domain.CommonField;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@SQLDelete(sql = "UPDATE member_interest SET deleted_at = now() WHERE member_interest_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_interest_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Interest interest;

    @Embedded
    private CommonField commonField = new CommonField();

    @Builder
    public MemberInterest(final Long id, final Member member, final Interest interest) {
        this.id = id;
        this.member = member;
        this.interest = interest;
    }

    public static List<MemberInterest> createNewMemberInterests(final List<Interest> interests,
                                                                final Member member) {
        return interests.stream()
                .map(interest -> MemberInterest.builder()
                        .member(member)
                        .interest(interest)
                        .build())
                .toList();
    }
}
