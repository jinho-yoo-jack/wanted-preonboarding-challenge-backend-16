package com.wanted.preonboarding.performance.application.service;

import com.wanted.preonboarding.performance.application.dto.PerformanceResponse;
import com.wanted.preonboarding.performance.application.exception.AccountNotAffordable;
import com.wanted.preonboarding.performance.application.exception.PerformanceNotFoundException;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.performance.infrasturcture.repository.PerformanceRepository;
import com.wanted.preonboarding.performanceSeat.domain.event.EnablePerformanceReservationEvent;
import com.wanted.preonboarding.performanceSeat.domain.event.SeatSoldOutEvent;
import com.wanted.preonboarding.reservation.domain.event.ValidateReservationRequestEvent;
import com.wanted.preonboarding.reservation.domain.event.WaitToReserveEvent;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final ApplicationEventPublisher eventPublisher;

    @TransactionalEventListener(SeatSoldOutEvent.class)
    public void updatePerformanceDisabled(final SeatSoldOutEvent seatSoldOutEvent) {
        // 공연의 예약 가능 여부 확인 및 설정
        Performance performance = getPerformance(seatSoldOutEvent.getPerformanceId());

        performance.disableReservation();
    }

    @TransactionalEventListener(EnablePerformanceReservationEvent.class)
    public void updatePerformanceEnabled(final EnablePerformanceReservationEvent enablePerformanceReservationEvent) {
        UUID performanceIdValue = enablePerformanceReservationEvent.getPerformanceIdValue();
        Performance performance = getPerformance(performanceIdValue);
        performance.enableReservation();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addWaitingReservation(final UserInfo userInfo , final UUID performanceId) {
        Performance performance = getPerformance(performanceId);
        eventPublisher.publishEvent(WaitToReserveEvent.of(userInfo, performance));
    }

    @Transactional(readOnly = true)
    public List<PerformanceResponse> findPerformancesInReserveState(final String isReserve) {
        return performanceRepository.findAllByReserveState(isReserve)
                .stream()
                .map(PerformanceResponse::from)
                .toList();
    }

    @TransactionalEventListener(value = ValidateReservationRequestEvent.class)
    public void validatePerformanceExistence(final ValidateReservationRequestEvent validateReservationRequestEvent) {
        Performance performance = getPerformance(validateReservationRequestEvent.getPerformanceId());

        if(!performance.isAccountAffordable(validateReservationRequestEvent.getAccount())) {
            throw new AccountNotAffordable();
        }
    }

    private Performance getPerformance(final UUID performanceId) {
        return performanceRepository.findById(performanceId)
                .orElseThrow(PerformanceNotFoundException::new);
    }
}
