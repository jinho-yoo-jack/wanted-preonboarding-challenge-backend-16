package com.wanted.preonboarding.domain.performance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.domain.performance.domain.entity.PerformanceHall;
import com.wanted.preonboarding.domain.performance.domain.entity.PerformanceHallSeat;

public interface PerformanceHallSeatRepository extends JpaRepository<PerformanceHallSeat, Long> {

	List<PerformanceHallSeat> findAllByIdIn(List<Long> hallSeatIdList);
}
