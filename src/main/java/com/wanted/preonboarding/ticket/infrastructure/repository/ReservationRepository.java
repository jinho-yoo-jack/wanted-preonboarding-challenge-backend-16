package com.wanted.preonboarding.ticket.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.enumeration.ReservationStatus;
import com.wanted.preonboarding.ticket.domain.vo.SeatInfo;
import com.wanted.preonboarding.ticket.domain.vo.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	List<Reservation> findByUserInfoAndReservationStatus(UserInfo userInfo, ReservationStatus reservationStatus);

	boolean existsReservationByPerformanceIdAndSeatInfo(UUID performanceId, SeatInfo seatInfo);

	Reservation findReservationByPerformanceIdAndSeatInfo(UUID performanceId, SeatInfo seatInfo);
}
