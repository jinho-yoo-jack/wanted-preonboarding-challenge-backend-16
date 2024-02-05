package com.wanted.preonboarding.ticket.service;

import static com.wanted.preonboarding.ticket.exception.ExceptionMessage.NOT_FOUND_PERFORMANCE;

import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.ticket.domain.event.PerformanceCancelEvent;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.exception.EntityNotFound;
import com.wanted.preonboarding.ticket.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.repository.ReservationRepository;
import com.wanted.preonboarding.ticket.service.dto.request.PerformanceCheckRequestDto;
import com.wanted.preonboarding.ticket.service.dto.response.PerformanceCheckResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional(readOnly = true)
    public List<PerformanceCheckResponseDto> getPerformances(final PerformanceCheckRequestDto request) {
        log.info("getPerformances request: {}", request);
        final List<Performance> reservablePerformances = performanceRepository.findAllByStatus(request.status());
        return reservablePerformances.stream()
                .map(PerformanceCheckResponseDto::of)
                .toList();
    }

    @Transactional
    public void cancel(final UUID performanceId) {
        final Performance findPerformance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new EntityNotFound(NOT_FOUND_PERFORMANCE.getMessage()));

        // 공연 취소
        findPerformance.cancel();

        // 이벤트
        log.info("cancel performance: {}, stauts: {}", findPerformance.getName(), findPerformance.getStatus());
        publisher.publishEvent(new PerformanceCancelEvent(findPerformance));

        log.info("Cancel Performance");
    }
}
