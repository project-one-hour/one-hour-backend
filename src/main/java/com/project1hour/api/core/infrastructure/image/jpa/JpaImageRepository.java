package com.project1hour.api.core.infrastructure.image.jpa;

import com.project1hour.api.core.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaImageRepository extends JpaRepository<Image, Long> {
}
