package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.request.DiscountRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationCancelRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationCreateRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationFindRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationCancelResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationCreateResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationFindResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.exception.*;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final DiscountService discountService;

    // 예약
    @Transactional
    public ReservationCreateResponse reserve(ReservationCreateRequest request) {
        log.info("ReservationService.reserve");
        Performance performance = findPerformance(request);
        PerformanceSeatInfo seatInfo = findSeatInfo(request);
        checkReservationAvailable(seatInfo);

        //할인된 가격 가져오기
        DiscountRequest discountRequest = getDiscountRequest(performance);
        int discountedPrice = discountService.discountPrice(discountRequest);

        //예약 가능한지 확인
        isAffordable(request, discountedPrice);

        //예매 진행 및 좌석 상태 변경
        Reservation save = reservationRepository.save(Reservation.of(request, performance));
        changeSeatStatus(seatInfo);
        return save.toReservationCreateResponse();
    }

    // 조회
    @Transactional(readOnly = true)
    public List<ReservationFindResponse> findReservation(ReservationFindRequest request) {
        List<Reservation> reservations = reservationRepository.findByNameAndPhoneNumber(request.getReservationName(), request.getReservationPhoneNumber());
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException("예매된 공연이 없습니다.");  //TODO: 상수로 변경
        } else {
            return reservations.stream().map(Reservation::toReservationFindResponse).collect(Collectors.toList());
        }
    }

    //TODO: 취소
    // 1. 좌석 가져오기 및 있는지 확인
    // 2. 공연이 disable이면 enable?
    // 3. 좌석 정보 변경
    // 4. 취소된 좌석과 관련된 알림이 있는지 확인
    // 5. 알림 보내기
    @Transactional
    public ReservationCancelResponse cancelReservation(ReservationCancelRequest request) {
        Reservation reservation = findReservation(request);
        PerformanceSeatInfo seatInfo = findSeatInfoWithReservation(reservation);

        //좌석정보 확인 및 좌석의 예약 상태 수정
        checkSeatInfoReserved(seatInfo);
        changeSeatStatus(seatInfo);
        deleteReservation(reservation);
        return reservation.toReservationCancelResponse();
    }

    private void deleteReservation(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    private PerformanceSeatInfo findSeatInfoWithReservation(Reservation reservation) {
        return performanceSeatInfoRepository.findByPerformanceIdAndRoundAndSeatAndLine(
                reservation.getPerformance().getId(),
                reservation.getRound(),
                reservation.getSeat(),
                reservation.getLine()
        ).orElseThrow(() -> new PerformanceNotFoundException("좌석 정보가 존재하지 않습니다."));
    }

    private Reservation findReservation(ReservationCancelRequest request) {
        return reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new ReservationNotFoundException("예매된 공연이 없습니다."));
    }

    private static void checkSeatInfoReserved(PerformanceSeatInfo seatInfo) {
        if (seatInfo.getIsReserve().equalsIgnoreCase("enable")) {
            throw new NoAvailableCancelSeatException("좌석을 취소할 수 없습니다 (예매되어있지 않는 좌석)");
        }
    }

    private static void changeSeatStatus(PerformanceSeatInfo seatInfo) {
        seatInfo.changeIsReserveStatus();
    }

    private void isAffordable(ReservationCreateRequest request, int discountedPrice) {
        if (request.getBalance() < discountedPrice) {
            throw new NotEnoughBalanceException("잔고가 부족합니다.");
        }
    }

    private static DiscountRequest getDiscountRequest(Performance performance) {
        LocalDateTime reserveDateTime = LocalDateTime.now();
        return performance.toDiscountRequest(reserveDateTime);
    }

    private Performance findPerformance(ReservationCreateRequest reserveCreateRequest) {
        return performanceRepository.findById(reserveCreateRequest.getPerformanceId())
                .orElseThrow(() -> new PerformanceNotFoundException("공연 정보가 존재하지 않습니다.")); //TODO: 분리 및 상수화
    }

    private static void checkReservationAvailable(PerformanceSeatInfo seatInfo) {
        if (seatInfo.getIsReserve().equalsIgnoreCase("disable")) {
            throw new SeatAlreadyReservedException("좌석이 매진되었습니다.");
        }
    }

    private PerformanceSeatInfo findSeatInfo(ReservationCreateRequest request) {
        return performanceSeatInfoRepository.findByPerformanceIdAndRoundAndSeatAndLine(
                request.getPerformanceId(),
                request.getRound(),
                request.getSeat(),
                request.getLine()        //TODO: String 상수화 및 분리
        ).orElseThrow(() -> new PerformanceSeatInfoNotFound("좌석 정보가 존재하지 않습니다."));
    }
}
