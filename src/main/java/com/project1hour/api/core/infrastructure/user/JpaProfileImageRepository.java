package com.project1hour.api.core.infrastructure.user;

import com.project1hour.api.core.domain.member.profileinfo.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
