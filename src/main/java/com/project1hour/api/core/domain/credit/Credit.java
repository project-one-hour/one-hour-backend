package com.project1hour.api.core.domain.credit;

import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.global.domain.CommonField;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@SQLDelete(sql = "UPDATE credit SET deleted_at = now() WHERE credit_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Credit {

    private static final int WELCOME_CREDIT_COUNT = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private int creditCount;

    @Embedded
    private CommonField commonField = new CommonField();

    @Builder
    public Credit(final Long id, final int creditCount, final Member member) {
        this.id = id;
        this.creditCount = creditCount;
        this.member = member;
    }

    public static Credit createWelcomeCredit(final Member member) {
        return Credit.builder()
                .creditCount(WELCOME_CREDIT_COUNT)
                .member(member)
                .build();
    }

    public boolean hasMoreCredit(int creditCount) {
        return this.creditCount >= creditCount;
    }
}
