package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.ReservationCancelAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationCancelAlarmRepository extends JpaRepository<ReservationCancelAlarm, Long> {
    List<ReservationCancelAlarm> findAllByPerformanceId(String performanceId);
}
