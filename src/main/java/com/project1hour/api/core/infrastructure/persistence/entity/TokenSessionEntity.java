package com.project1hour.api.core.infrastructure.persistence.entity;

import com.project1hour.api.core.domain.user.entity.User;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "token_session")
@SQLDelete(sql = "UPDATE auth_provider SET deleted_at = now() WHERE auth_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenSessionEntity {

    @Id
    @Tsid
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
