package com.wanted.preonboarding.performance.application;

import com.wanted.preonboarding.performance.domain.PerformanceShowing;
import com.wanted.preonboarding.performance.infrastructure.repository.ShowingRepository;
import com.wanted.preonboarding.performance.presentation.dto.PerformanceResponse;
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
public class PerformanceService {
    private final ShowingRepository showingRepository;

    public List<PerformanceResponse> getAllPerformanceInfoList(boolean isReserve) {
        return showingRepository.findByReservationAvailable(isReserve)
            .stream()
            .map(PerformanceResponse::of)
            .toList();
    }

    public PerformanceResponse getPerformanceInfoDetail(String name) {
        return PerformanceResponse.of(showingRepository.findByPerformanceName(name));
    }


}
