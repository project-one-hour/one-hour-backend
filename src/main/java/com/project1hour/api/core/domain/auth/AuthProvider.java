package com.project1hour.api.core.domain.auth;

import com.project1hour.api.core.domain.member.Member;
import com.project1hour.api.core.domain.member.Provider;
import com.project1hour.api.global.domain.CommonField;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE auth_provider SET deleted_at = now() WHERE auth_provider_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_provider_id")
    private Long id;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Provider provider;

    @Column(nullable = false, unique = true)
    private String providerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private CommonField commonField = new CommonField();

    @Builder
    public AuthProvider(final Long id, final String email, final Member member, final Provider provider,
                        final String providerId) {
        this.id = id;
        this.email = email;
        this.member = member;
        this.provider = provider;
        this.providerId = providerId;
    }
}
