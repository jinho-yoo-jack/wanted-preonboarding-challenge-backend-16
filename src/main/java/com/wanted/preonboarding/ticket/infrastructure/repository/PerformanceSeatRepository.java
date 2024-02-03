package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerformanceSeatRepository extends JpaRepository<PerformanceSeatInfo,Long> {

    Optional<PerformanceSeatInfo> findByPerformanceNameAndSeatLineAndSeatNumber(String performanceName,char seatLine, int seatNumber);
}
