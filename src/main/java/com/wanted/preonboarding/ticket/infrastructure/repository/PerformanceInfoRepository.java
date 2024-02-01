package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceDetail;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PerformanceInfoRepository extends JpaRepository<PerformanceSeatInfo, Integer> {

    @Modifying
    @Query("UPDATE PerformanceSeatInfo p " +
              "SET p.isReserve = :isReserve " +
            "WHERE p.performanceId = :performanceId " +
            "  AND p.seat = :seat")
    void updateIsReserve(@Param("performanceId") UUID performanceId, @Param("seat") int seat, @Param("isReserve") String isReserve);

    boolean existsByPerformanceIdAndIsReserve(UUID performanceId, String isReserve);

    @Query("SELECT p, f.name" +
            " FROM PerformanceSeatInfo p" +
            " JOIN p.performance f " +
            "   ON p.performanceId = f.id " +
            "WHERE p.performanceId = :performanceId" +
            "  AND p.round = :round" +
            "  AND p.seat = :seat")
    PerformanceDetail findByPerformanceAndSeat(@Param("performanceId") UUID performanceId, @Param("round") int round, @Param("seat") int seat);
}
