package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {
    List<Performance> findByIsReserve(String isReserve);
    Performance findByName(String name);
    @Query(value = "SELECT EXISTS(SELECT is_reserve FROM performance WHERE id = ? AND is_reserve = ?)",nativeQuery = true)
    int findByIdAndIsReserve(UUID id, String isReserve);
}
