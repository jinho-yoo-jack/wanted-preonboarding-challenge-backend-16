package com.wanted.preonboarding.reservation.infrastructure.repository;

import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByNameAndPhoneNumber(final String name, final String phoneNumber);

    boolean existsByPerformanceAndSeatInfo(final Performance performance, final SeatInfo seatInfo);
}
