package com.project1hour.api.core.infrastructure.repository;

import com.project1hour.api.core.infrastructure.persistence.ProfileImageEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProfileImageRepository extends JpaRepository<ProfileImageEntity, Long> {

    List<ProfileImageEntity> findByUserId(Long userId);
}
