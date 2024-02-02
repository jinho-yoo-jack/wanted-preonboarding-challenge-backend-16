package com.wanted.preonboarding.performance.application;

import com.wanted.preonboarding.performance.domain.Perform;
import com.wanted.preonboarding.performance.domain.ReservationCancelSubscribe;
import com.wanted.preonboarding.performance.framwork.infrastructure.repository.PerformRepository;
import com.wanted.preonboarding.performance.framwork.infrastructure.repository.ReservationCancelSubscribeRepository;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformanceResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformService {
    private final PerformRepository performRepository;
    private final ReservationCancelSubscribeRepository subscribeRepository;

    public List<PerformanceResponse> getAllPerformanceInfoList(boolean isReserve) {
        return performRepository.findByReservationAvailable(isReserve)
            .stream()
            .map(PerformanceResponse::of)
            .toList();
    }

    public PerformanceResponse getPerformanceInfoDetail(UUID id) {
        Perform perform = performRepository.findById(id).orElseThrow(
            EntityNotFoundException::new);
        return PerformanceResponse.of(perform);
    }


    public boolean isAvailable(UUID performId) {
        Perform perform = performRepository.findById(performId)
            .orElseThrow(EntityNotFoundException::new);

        return perform.isReservationAvailable();
    }

    public int getPaymentAmount(UUID performId){
        Perform perform = performRepository.findById(performId)
            .orElseThrow(EntityNotFoundException::new);

        return perform.calculatePaymentFee();
    }

    @Transactional
    public void subscribe(UUID performId, UUID userId) {
        Perform perform = performRepository.findById(performId)
            .orElseThrow(EntityNotFoundException::new);

        ReservationCancelSubscribe observer = ReservationCancelSubscribe.create(
            perform, userId);

        subscribeRepository.save(observer);
    }

    @Transactional
    public void soldOut(UUID performId) {
        Perform perform = performRepository.findById(performId)
            .orElseThrow(EntityNotFoundException::new);

        perform.soldOut();
    }
}
