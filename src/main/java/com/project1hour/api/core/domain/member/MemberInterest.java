package com.project1hour.api.core.domain.member;

import com.project1hour.api.core.domain.interest.Interest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_interest_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id")
    private Interest interest;

    @Builder
    public MemberInterest(final Long id, final Interest interest, final Member member) {
        this.id = id;
        this.interest = interest;
        this.member = member;
    }

    public static List<MemberInterest> createNewMemberInterests(final List<Interest> interests, final Member member) {
        return interests.stream()
                .map(o -> MemberInterest.builder()
                        .interest(o)
                        .member(member)
                        .build())
                .toList();
    }
}
