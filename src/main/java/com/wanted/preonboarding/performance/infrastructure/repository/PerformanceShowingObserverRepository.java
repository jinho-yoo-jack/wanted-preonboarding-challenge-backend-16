package com.wanted.preonboarding.performance.infrastructure.repository;

import com.wanted.preonboarding.performance.domain.PerformSubscribe;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceShowingObserverRepository extends JpaRepository<PerformSubscribe, UUID> {

	List<PerformSubscribe> findByPerformId(UUID id);
}
