package com.wanted.preonboarding.ticket.application.repository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.enums.ReservationAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Integer> {

    List<PerformanceSeatInfo> findAllByPerformanceAndIsReserve(Performance performance, ReservationAvailability isReserve);

    Optional<PerformanceSeatInfo> findByPerformanceIdAndRoundAndLineAndSeat(UUID performanceId, int round, String line, int seat);



}

