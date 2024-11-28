package com.project1hour.api.core.infrastructure.repository;

import com.project1hour.api.core.infrastructure.persistence.OauthInfoEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOauthInfoRepository extends JpaRepository<OauthInfoEntity, Long> {

    Optional<OauthInfoEntity> findByProviderAndUserSocialId(String providerType, String userSocialId);

}
