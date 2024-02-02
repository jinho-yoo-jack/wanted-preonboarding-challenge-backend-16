package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;


public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Long> {
    @Query("SELECT m FROM PerformanceSeatInfo m WHERE m.performanceId = :performanceId AND m.round = :round AND m.line = :line AND m.seat = :seat")
    PerformanceSeatInfo findByPerformanceIdJpql(@Param("performanceId") UUID performanceId, @Param("round") int round, @Param("line") Character line, @Param("seat") int seat);

    @Transactional
    @Modifying
    @Query("UPDATE PerformanceSeatInfo e SET e.isReserve = :enableValue")
    void changeReserveStatus(@Param("enableValue") String enable);

}