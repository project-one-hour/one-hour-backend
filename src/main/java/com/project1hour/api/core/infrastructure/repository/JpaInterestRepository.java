package com.project1hour.api.core.infrastructure.repository;

import com.project1hour.api.core.infrastructure.persistence.InterestEntity;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaInterestRepository extends JpaRepository<InterestEntity, Long> {

    @Query("SELECT COUNT(i.id) = :size FROM InterestEntity i WHERE i.id IN :ids")
    boolean existsAllByIdIn(@Param("ids") Collection<Long> ids, @Param("size") long size);
}
