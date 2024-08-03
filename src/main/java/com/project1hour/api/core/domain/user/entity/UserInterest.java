package com.project1hour.api.core.domain.user.entity;

import com.project1hour.api.core.domain.user.User;
import com.project1hour.api.core.domain.user.value.Interest;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@SQLDelete(sql = "UPDATE user_interest SET deleted_at = now() WHERE user_interest_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInterest {

    @Id
    @Tsid
    @Column(name = "user_interest_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Interest interest;

    @Builder
    public UserInterest(final Long id, final User user, final Interest interest) {
        this.id = id;
        this.user = user;
        this.interest = interest;
    }
}
