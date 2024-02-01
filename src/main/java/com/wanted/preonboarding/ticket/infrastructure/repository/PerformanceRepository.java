package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {

    @Query("select p from Performance p order by p.startDate asc")
    List<Performance> findAllAsc();
    List<Performance> findByIsReserve(String isReserve);
    Performance findByName(String name);

    @Modifying
    @Query("UPDATE Performance p " +
            "  SET p.isReserve = :isReserve " +
            "WHERE p.id = :performanceId")
    void updateIsReserve(@Param("performanceId") UUID performanceId, @Param("isReserve") String isReserve);
}
