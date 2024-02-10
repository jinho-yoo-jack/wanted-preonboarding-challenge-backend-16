package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.request.FindReserveRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.PerformanceListRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReserveInfoRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.WaitReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.FindReserveResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceListResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReserveInfoResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.WaitReservationResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final ReservationRepository reservationRepository;
    private long totalAmount = 0L;

    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
            .stream()
            .map(PerformanceInfo::of)
            .toList();
    }

    public PerformanceInfo getPerformanceInfoDetail(String name) {
        return PerformanceInfo.of(performanceRepository.findByName(name));
    }

    public ReserveInfoResponse reserve(ReserveInfoRequest request) {
        log.info("reserveInfo ID => {}", request.getPerformanceId());

        // id가 있는지 조회
        Performance info = performanceRepository.findById(request.getPerformanceId()).orElseThrow(EntityNotFoundException::new);

        String enableReserve = info.getIsReserve();

        // 예약 가능 상태일 때
        if (enableReserve.equalsIgnoreCase("enable")) {
            // 1. 결제
            info.calculateAmount(request.getAmount());

            // 2. 전시회 ID, 회차, 좌석 정보 찾기
            if (!performanceSeatInfoRepository.isAvailable(request)) {
                throw new IllegalArgumentException("해당 자리는 신청 할 수 없습니다.");
            }

            // 3. 예매 진행
            reservationRepository.save(Reservation.of(request));

            return ReserveInfoResponse.of(request);
        } else {
            throw new IllegalArgumentException("예약 가능한 상태가 아닙니다.");
        }
    }

    public FindReserveResponse findReserveInfo(FindReserveRequest request) {
        return reservationRepository.findReserveInfo(request);
    }

    public List<PerformanceListResponse> findAvailablePerformance(PerformanceListRequest request) {
        return performanceRepository.findPerformanceAll(request);
    }

    public WaitReservationResponse waitReservation(WaitReservationRequest request) {
        return null;
    }
}
