package com.wanted.preonboarding.ticket.application.performance.service;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.common.exception.EntityNotFoundException;
import com.wanted.preonboarding.ticket.application.performance.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceDetail;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.request.RegisterPerformance;
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
import java.util.stream.IntStream;

import static com.wanted.preonboarding.core.domain.response.ResponseHandler.MESSAGE_SUCCESS;
import static com.wanted.preonboarding.core.domain.response.ResponseHandler.createResponse;
import static com.wanted.preonboarding.ticket.application.common.exception.ExceptionStatus.NOT_FOUND_INFO;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceService {
    // 책임 : 공연 및 전시 정보 조회(목록, 상세 조회), 좌석 정보 조회
    private final PerformanceRepository performanceRepository;

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
        List<PerformanceSeatInfo> seatInfoList = performance.getPerformanceSeatInfoList();
        return createResponse(HttpStatus.OK, MESSAGE_SUCCESS, PerformanceDetail.of(seatInfoList));
    }

    @Transactional
    public ResponseEntity<ResponseHandler<PerformanceDetail>> registerPerformance(RegisterPerformance request) {
        log.info("--- Register Performance ---");
        Performance performance = Performance.of(request);
        List<PerformanceSeatInfo> seatInfoList = registerSeatInfo(performance, request);

        performance.setPerformanceSeatInfoList(seatInfoList);
        performanceRepository.save(performance);
        return createResponse(HttpStatus.OK, MESSAGE_SUCCESS, PerformanceDetail.of(seatInfoList));
    }

    // ========== PRIVATE METHODS ========== //
    private Performance getPerformanceEntity(UUID id, int round) {
        return performanceRepository.findByIdAndRound(id, round)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_INFO));
    }

    private List<PerformanceInfo> getAllAvailalePerformances() {
        return performanceRepository.findAllByIsReserve(ReservationAvailability.AVAILABLE)
                .stream()
                .map(PerformanceInfo::of)
                .toList();
    }

    private List<PerformanceSeatInfo> registerSeatInfo(Performance performance, RegisterPerformance request) {
        char firstLine = 'A';
        int numberOfLines = request.totalSeat()/ request.seatPerLine();
        int seatPerLine = request.seatPerLine();
        return IntStream.range(0, numberOfLines)
                .boxed()
                .flatMap(i -> IntStream.rangeClosed(1, seatPerLine)
                        .mapToObj(seat -> {
                            char seatLine = (char) (firstLine + i);
                            return  PerformanceSeatInfo.of(performance, String.valueOf(seatLine), seat);
                        })
                )
                .toList();
    }

}
