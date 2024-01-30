package com.wanted.preonboarding.performance.infrastructure.repository;

import com.wanted.preonboarding.performance.domain.PerformanceReservation;
import com.wanted.preonboarding.performance.domain.vo.ReservationStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<PerformanceReservation, UUID> {

	Optional<PerformanceReservation> findByIdAndReservationStatus(
		UUID reservationId,
		ReservationStatus reservationStatus);

	List<PerformanceReservation> findByNameAndPhoneNumber(String userName, String phoneNumber);
}
