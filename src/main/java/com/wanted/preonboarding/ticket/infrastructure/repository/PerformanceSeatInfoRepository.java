package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Integer> {

    /**
     * ID 값으로 PerformanceSeatInfo 엔티티 조회
     * @param id must not be {@literal null}.
     * @return
     */
    Optional<PerformanceSeatInfo> findById(Integer id);

    /**
     * PerformanceSeatInfo 엔티티의 isReserve 필드 및 performance 필드-엔티티의 id를 통해 엔티티를 조회
     * @param isReserve
     * @param performanceId
     * @return
     */
    Optional<List<PerformanceSeatInfo>> findByIsReserveAndPerformance_id(String isReserve, UUID performanceId);

    /**
     * performance 필드 내의 id와 round 필드, seat 필드, line 필드를 통해 엔티티를 조회
     * @param seat
     * @param line
     * @param performanceId
     * @param round
     * @return
     */
    Optional<PerformanceSeatInfo> findBySeatAndLineAndPerformance_idAndPerformance_round(int seat, char line, UUID performanceId, int round);

    /**
     * isReserve와 Performance 필드의 id 필드를 통해 해당하는 엔티티의 갯수를 조회
     * @param isReserve
     * @param performaneId
     * @return
     */
    long countByIsReserveAndPerformance_id(String isReserve, UUID performaneId);
}
