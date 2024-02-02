package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.request.ReserveCreateRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReserveFindRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.ReserveFindResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.exception.PerformanceNotFoundException;
import com.wanted.preonboarding.ticket.exception.PerformanceSeatInfoNotFound;
import com.wanted.preonboarding.ticket.exception.ReservationNotFoundException;
import com.wanted.preonboarding.ticket.exception.SeatAlreadyReservedException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

public class ReservationService {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

    // TODO: 예약

    @Transactional
    public boolean reserve(ReserveCreateRequest request) {
        log.info("ReservationService.reserve");
        Performance performance = findPerformance(request);
        PerformanceSeatInfo seatInfo = performanceSeatInfoRepository.findByPerformanceIdAndRoundAndSeatAndLine(
                request.getPerformanceId(), request.getRound(),
                request.getSeat(), request.getLine()        //TODO: String 상수화 및 분리
        ).orElseThrow(() -> new PerformanceSeatInfoNotFound("좌석 정보가 존재하지 않습니다."));
        //TODO: 분리
        if (seatInfo.getIsReserve().equals("disable")) {
            throw new SeatAlreadyReservedException("좌석이 매진되었습니다.");
        }
        //TODO: 할인 정책 확인

        //TODO: 할인 후 구매 가능한지 잔고 확인
        return true;
    }

    private Performance findPerformance(ReserveCreateRequest reserveCreateRequest) {
        return performanceRepository.findById(reserveCreateRequest.getPerformanceId())
                .orElseThrow(() -> new PerformanceNotFoundException("공연 정보가 존재하지 않습니다.")); //TODO: 분리 및 상수화
    }

    @Transactional(readOnly = true)
    public List<ReserveFindResponse> findReservation(ReserveFindRequest request) {
        List<Reservation> reservations = reservationRepository.findByNameAndPhoneNumber(request.getReservationName(), request.getReservationPhoneNumber());
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException("예매된 공연이 없습니다.");  //TODO: 상수로 변경
        } else {
            return reservations.stream().map(Reservation::toReserveFindResponse).collect(Collectors.toList());
        }
    }

    //TODO: 취소


}
