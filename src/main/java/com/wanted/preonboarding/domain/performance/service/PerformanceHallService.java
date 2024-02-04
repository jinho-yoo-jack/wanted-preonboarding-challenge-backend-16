package com.wanted.preonboarding.domain.performance.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.domain.performance.domain.entity.PerformanceHall;
import com.wanted.preonboarding.domain.performance.domain.entity.PerformanceHallSeat;
import com.wanted.preonboarding.domain.performance.repository.PerformanceHallRepository;
import com.wanted.preonboarding.domain.performance.repository.PerformanceHallSeatRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PerformanceHallService {

    private final PerformanceHallRepository performanceHallRepository;
    private final PerformanceHallSeatRepository performanceHallSeatRepository;

    @Transactional(readOnly = true)
    public PerformanceHall getPerformanceHall(Long hallId) {

        return this.performanceHallRepository.findById(hallId)
            .orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<PerformanceHall> getPerformanceHall(List<Long> hallIdList) {

        return this.performanceHallRepository.findByIdIn(hallIdList);
    }

    @Transactional(readOnly = true)
    public PerformanceHallSeat getPerformanceHallSeat(Long hallSeatId) {

        return this.performanceHallSeatRepository.findById(hallSeatId)
            .orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<PerformanceHallSeat> getPerformanceHallSeat(List<Long> hallSeatIdList) {

        return this.performanceHallSeatRepository.findAllByIdIn(hallSeatIdList);
    }

}
