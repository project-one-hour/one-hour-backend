package com.project1hour.api.core.infrastructure.interest.jpa;

import com.project1hour.api.core.domain.interest.Interest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaInterestRepository extends JpaRepository<Interest, Long> {

    List<Interest> findByNameIn(List<String> names);
}
