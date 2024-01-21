package com.wanted.preonboarding.ticket.application.service;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.exception.EntityNotFoundException;
import com.wanted.preonboarding.ticket.application.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.application.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceDetail;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.enums.ReservationAvailability;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.wanted.preonboarding.core.domain.response.ResponseHandler.MESSAGE_SUCCESS;
import static com.wanted.preonboarding.core.domain.response.ResponseHandler.createResponse;
import static com.wanted.preonboarding.ticket.application.exception.ExceptionStatus.NOT_FOUND_INFO;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceService {
    // 책임 : 공연 및 전시 정보 조회(목록, 상세 조회), 좌석 정보 조회
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository seatInfoRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseHandler<List<PerformanceInfo>>> getAllPerformanceInfoList() {
        log.info("--- Get All Performance Info List ---");
        List<PerformanceInfo> performanceInfoList = getAllAvailalePerformances();
        return createResponse(HttpStatus.OK, MESSAGE_SUCCESS, performanceInfoList);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseHandler<PerformanceDetail>> getPerformanceInfoDetail(UUID id, int round) {
        log.info("--- Get Performance Info Detail ---");
        Performance performance = getPerformanceEntity(id, round);
        List<PerformanceSeatInfo> seatInfoList = getSeatInfoEntity(performance);
        return createResponse(HttpStatus.OK, MESSAGE_SUCCESS, PerformanceDetail.of(seatInfoList));
    }

    // ========== PRIVATE METHODS ========== //
    private Performance getPerformanceEntity(UUID id, int round) {
        return performanceRepository.findByIdAndRound(id, round)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_INFO));
    }
    private List<PerformanceSeatInfo> getSeatInfoEntity(Performance performance) {
        return seatInfoRepository.findAllByPerformanceAndIsReserve(performance, ReservationAvailability.AVAILABLE);
    }
    private List<PerformanceInfo> getAllAvailalePerformances() {
        return performanceRepository.findAllByIsReserve(ReservationAvailability.AVAILABLE)
                .stream()
                .map(PerformanceInfo::of)
                .toList();
    }

}
