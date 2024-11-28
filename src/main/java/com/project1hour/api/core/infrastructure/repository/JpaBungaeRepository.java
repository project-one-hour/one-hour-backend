package com.project1hour.api.core.infrastructure.repository;

import com.project1hour.api.core.infrastructure.persistence.BungaeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBungaeRepository extends JpaRepository<BungaeEntity, Long> {
}
