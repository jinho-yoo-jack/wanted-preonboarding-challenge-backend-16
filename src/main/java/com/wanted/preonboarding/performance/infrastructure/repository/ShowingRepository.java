package com.wanted.preonboarding.performance.infrastructure.repository;

import com.wanted.preonboarding.performance.domain.PerformanceShowing;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowingRepository extends JpaRepository<PerformanceShowing, UUID> {

	Optional<PerformanceShowing> findByReservationAvailable(boolean isReserve);

	PerformanceShowing findByPerformanceName(String name);
}
