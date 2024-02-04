package com.wanted.preonboarding.domain.performance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.domain.performance.domain.entity.PerformanceHall;


public interface PerformanceHallRepository extends JpaRepository<PerformanceHall, Long> {

	List<PerformanceHall> findByIdIn(List<Long> hallIdList);
}
