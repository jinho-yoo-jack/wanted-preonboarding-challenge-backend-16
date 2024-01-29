package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceResponse;
import com.wanted.preonboarding.ticket.infrastructure.repository.ShowingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
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
