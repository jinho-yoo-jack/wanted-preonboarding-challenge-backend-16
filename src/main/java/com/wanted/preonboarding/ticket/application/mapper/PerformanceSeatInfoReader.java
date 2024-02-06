package com.wanted.preonboarding.ticket.application.mapper;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class PerformanceSeatInfoReader {

    public static final String PERFORMANCE_SEAT_INFO_ERROR_FORMAT = "[%d] 는 존재하지 않는 좌석 정보입니다.";
    public static final String RESERVATION_SEAT_INFO_ERROR_FORMAT = "공연 정보 [%s] 에 존재하지 않는 좌석 정보입니다.";

    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

    public PerformanceSeatInfo findById(Long performanceSeatInfoId) {
        return performanceSeatInfoRepository.findById(performanceSeatInfoId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(PERFORMANCE_SEAT_INFO_ERROR_FORMAT, performanceSeatInfoId)));
    }

    public PerformanceSeatInfo findBySeatInfo(UUID performanceId, int round, char line, int gate, int seat) {
        return performanceSeatInfoRepository.findBySeatInfo(performanceId, round, line, gate, seat)
                .orElseThrow(() -> new EntityNotFoundException(String.format(RESERVATION_SEAT_INFO_ERROR_FORMAT, performanceId)));
    }
}
