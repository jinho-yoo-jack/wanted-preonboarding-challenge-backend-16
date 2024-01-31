package com.wanted.preonboarding.performance.infrasturcture.repository;

import com.wanted.preonboarding.performance.domain.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {

    List<Performance> findAllByReserveState(String isReserve);
}
