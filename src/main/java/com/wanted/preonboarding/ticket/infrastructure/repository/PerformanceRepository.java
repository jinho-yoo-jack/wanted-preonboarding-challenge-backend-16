package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {

    List<Performance> findByIsReserve(String isReserve);

    Optional<Performance> findById(UUID id);


    @Modifying
    @Query("UPDATE Performance p SET  p.isReserve = :newIsReserveStatus WHERE p.id = :performanceId")
    void updateIsReserveStatus(@Param("performanceId") UUID performanceId, @Param("newIsReserveStatus") String newIsReserveStatus);
    Performance findByNameAndTypeAndRoundAndStartDate(
            String name, int type, int round, Date startDate);



}
