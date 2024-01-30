package com.wanted.preonboarding.performance.infrastructure.repository;

import com.wanted.preonboarding.performance.domain.PerformanceReservation;
import com.wanted.preonboarding.performance.domain.vo.ReservationStatus;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<PerformanceReservation, UUID> {

	Optional<PerformanceReservation> findByIdAndReservationStatus(
		UUID reservationId,
		ReservationStatus reservationStatus);

}
