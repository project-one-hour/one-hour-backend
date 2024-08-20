package com.project1hour.api.core.infrastructure.user.jpa;

import com.project1hour.api.core.domain.user.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
