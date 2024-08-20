package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.core.domain.user.value.AuthInfo;
import com.project1hour.api.core.domain.user.value.AuthProvider;
import com.project1hour.api.global.domain.AbstractEntity;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE auth_provider SET deleted_at = now() WHERE auth_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth extends AbstractEntity<Long> {

    @Id
    @Tsid
    @Column(name = "auth_id")
    private Long id;

    @OneToOne(mappedBy = "userAuth")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    @Embedded
    private AuthInfo info;

    @Builder
    public Auth(final Long id, User user, final AuthProvider provider, final AuthInfo authInfo) {
        this.id = id;
        this.user = user;
        this.provider = provider;
        this.info = authInfo;
    }
}
