package com.wanted.preonboarding.performance.application.service;

import com.wanted.preonboarding.performance.application.dto.PerformanceResponse;
import com.wanted.preonboarding.performance.application.exception.PerformanceNotFoundException;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.performance.infrasturcture.repository.PerformanceRepository;
import com.wanted.preonboarding.performanceSeat.domain.event.EnablePerformanceReservationEvent;
import com.wanted.preonboarding.performanceSeat.domain.event.SeatSoldOutEvent;
import com.wanted.preonboarding.reservation.domain.event.WaitToReserveEvent;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final ApplicationEventPublisher eventPublisher;

    @EventListener(SeatSoldOutEvent.class)
    @Transactional
    public void updatePerformanceDisabled(final SeatSoldOutEvent seatSoldOutEvent) {
        // 공연의 예약 가능 여부 확인 및 설정
        Performance performance = performanceRepository.findById(seatSoldOutEvent.getPerformanceId())
                .orElseThrow(PerformanceNotFoundException::new);

        performance.disableReservation();
    }

    @EventListener(EnablePerformanceReservationEvent.class)
    @Transactional
    public void updatePerformanceEnabled(final EnablePerformanceReservationEvent enablePerformanceReservationEvent) {
        Performance performance = enablePerformanceReservationEvent.getPerformance();
        performance.enableReservation();
    }

    @Transactional
    public void addWaitingReservation(final UserInfo userInfo , final UUID performanceId) {
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(PerformanceNotFoundException::new);
        eventPublisher.publishEvent(WaitToReserveEvent.of(userInfo, performance));
    }

    @Transactional(readOnly = true)
    public List<PerformanceResponse> findPerformancesInReserveState(String isReserve) {
        return performanceRepository.findAllByReserveState(isReserve)
                .stream()
                .map(PerformanceResponse::from)
                .toList();
    }
}
