package com.wanted.preonboarding.ticket.domain.infrastructure;

import com.wanted.preonboarding.ticket.domain.Performance;
import com.wanted.preonboarding.ticket.domain.PerformanceSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Integer> {

    Optional<PerformanceSeatInfo> findByPerformanceAndLineAndSeat(Performance performance, String line, int seat);
}
