package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface JpaPerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Long>, PerformanceSeatInfoRepository {

    @Query("""
            SELECT ps
            FROM PerformanceSeatInfo ps
            WHERE ps.performanceId = :performanceId
            AND ps.round = :round
            AND ps.line = :line
            AND ps.gate = :gate
            AND ps.seat = :seat
           """)
    @Override
    Optional<PerformanceSeatInfo> findBySeatInfo(UUID performanceId, int round, char line, int gate, int seat);
}
