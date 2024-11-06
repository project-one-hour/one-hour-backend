package com.project1hour.api.core.infrastructure.persistence.repository;

import com.project1hour.api.core.infrastructure.persistence.entity.OauthInfoEntity;
import com.project1hour.api.core.infrastructure.persistence.entity.ProviderType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOauthInfoRepository extends JpaRepository<OauthInfoEntity, Long> {

    Optional<OauthInfoEntity> findByProviderTypeAndUserSocialId(ProviderType providerType, String userSocialId);

}
