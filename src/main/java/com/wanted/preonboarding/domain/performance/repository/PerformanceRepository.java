package com.wanted.preonboarding.domain.performance.repository;

import com.wanted.preonboarding.domain.performance.domain.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {

	List<Performance> findAllByIdIn(List<UUID> performanceIdList);

}
