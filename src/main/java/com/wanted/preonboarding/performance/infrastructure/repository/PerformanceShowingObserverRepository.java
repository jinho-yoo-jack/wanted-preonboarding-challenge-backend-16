package com.wanted.preonboarding.performance.infrastructure.repository;

import com.wanted.preonboarding.performance.domain.PerformanceShowingObserver;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceShowingObserverRepository extends JpaRepository<PerformanceShowingObserver, UUID> {

	List<PerformanceShowingObserver> findByShowingId(UUID id);
}
