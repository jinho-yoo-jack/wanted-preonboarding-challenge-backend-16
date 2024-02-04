package com.wanted.preonboarding.ticket.domain.performance;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PerformanceRepository extends JpaRepository<Performance, UUID>, PerformanceSearchRepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
         select ps from PerformanceSeatInfo ps
         where ps.performanceId = :performanceId
         AND ps.round = :round
         AND ps.line = :line
         AND ps.seat = :seat
         """)
    Optional<PerformanceSeatInfo> findSeatByIdAndInfo(@Param("performanceId") UUID performanceId,
                                            @Param("round") int round,
                                            @Param("line") String line,
                                            @Param("seat") int seat);

    @Query("select p.name from Performance p where p.id = :id")
    Optional<String> findNameById(@Param("id") UUID id);

}
