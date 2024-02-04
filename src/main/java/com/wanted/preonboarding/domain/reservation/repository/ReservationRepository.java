package com.wanted.preonboarding.domain.reservation.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.domain.reservation.domain.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	boolean existsByPerformanceIdAndHallSeatId(
		UUID performanceId,
		Long performanceHallSeatId);
}
