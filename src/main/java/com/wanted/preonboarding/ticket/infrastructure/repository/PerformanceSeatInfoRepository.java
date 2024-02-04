package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.SeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Integer> {
    PerformanceSeatInfo findByPerformanceIdAndSeatInfo(UUID id, SeatInfo seatInfo);
}
