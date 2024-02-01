package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.ReservationAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubscribeReservationRepository extends JpaRepository<ReservationAlarm, Integer> {
    List<ReservationAlarm> findByPerformanceId(UUID performanceId);
}
