package com.wanted.preonboarding.reservation.infrastructure.repository;

import com.wanted.preonboarding.common.model.PerformanceId;
import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.reservation.domain.entity.Reservation;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>, ReservationCustomRepository {

    boolean existsByPerformanceIdAndSeatInfo(final PerformanceId performanceId, final SeatInfo seatInfo);
}
