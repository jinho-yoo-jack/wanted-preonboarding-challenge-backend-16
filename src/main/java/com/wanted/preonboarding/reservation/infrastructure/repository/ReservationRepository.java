package com.wanted.preonboarding.reservation.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.reservation.domain.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	Reservation findByPerformanceIdAndNameAndPhoneNumberAndTypeNot(UUID performanceId,String name, String phoneNumber,Integer type);
    List<Reservation> findByNameAndPhoneNumber(String name, String phoneNumber);
    List<Reservation> findByPerformanceIdAndRoundAndLineAndSeat(UUID performanceId, Integer Round,String line,Integer seat);
    List<Reservation> findByPerformanceIdAndRoundAndLineAndSeatAndType(UUID performanceId, Integer Round,String line,Integer seat,Integer type);
}
