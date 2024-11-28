package com.project1hour.api.core.infrastructure.repository;

import com.project1hour.api.core.infrastructure.persistence.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaParticipantRepository extends JpaRepository<ParticipantEntity, Integer> {
}
