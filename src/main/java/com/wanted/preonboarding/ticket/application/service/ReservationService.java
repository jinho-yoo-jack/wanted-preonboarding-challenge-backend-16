package com.wanted.preonboarding.ticket.application.service;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.CancellationEvent;
import com.wanted.preonboarding.ticket.domain.dto.PaymentResponse;
import com.wanted.preonboarding.ticket.domain.dto.RequestReservation;
import com.wanted.preonboarding.ticket.domain.dto.ReservationResponse;
import com.wanted.preonboarding.ticket.application.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.application.repository.ReservationRepository;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.enums.ReservationAvailability;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

import static com.wanted.preonboarding.core.domain.response.ResponseHandler.MESSAGE_SUCCESS;
import static com.wanted.preonboarding.core.domain.response.ResponseHandler.createResponse;
import static com.wanted.preonboarding.ticket.domain.enums.ReservationAvailability.AVAILABLE;
import static com.wanted.preonboarding.ticket.domain.enums.ReservationAvailability.OCCUPIED;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    // 책임 : 예약 진행 및 취소, 예약 내역 조회
    private final PaymentService paymentService;
    private final ReservationRepository reservationRepository;
    private final PerformanceSeatInfoRepository seatInfoRepository;
    private final ApplicationEventPublisher eventPublisher;
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseHandler<ReservationResponse>> getReservationInfo(
            final String name,
            final String phoneNumber
    ) {
        validateNameAndPhoneNumber(name, phoneNumber);
        Reservation reservation = getReservationEntity(name, phoneNumber);
        Performance performance = reservation.getPerformanceSeatInfo().getPerformance();

        return createResponse(HttpStatus.OK, MESSAGE_SUCCESS, ReservationResponse.of(reservation, performance));
    }
    @Transactional
    public ResponseEntity<ResponseHandler<ReservationResponse>> processReservation(RequestReservation requestReservation) {
        PerformanceSeatInfo seatInfo = getSeatInfoEntity(requestReservation);
        Performance performance = seatInfo.getPerformance();
        Reservation reservation = Reservation.of(requestReservation, seatInfo);

        checkSeatAvailability(seatInfo);

        PaymentResponse paymentResponse = paymentService.processPayment(reservation, requestReservation.balance());

        reservationRepository.save(reservation);
        seatInfo.modifyReservationAvailability(OCCUPIED);
        return createResponse(HttpStatus.CREATED, MESSAGE_SUCCESS, ReservationResponse.of(reservation, performance, paymentResponse));
    }

    @Transactional
    public ResponseEntity<ResponseHandler<Void>> cancelReservation(String name, String phoneNumber) {
        Reservation reservation = getReservationEntity(name, phoneNumber);
        reservationRepository.delete(reservation);
        reservation.getPerformanceSeatInfo().modifyReservationAvailability(AVAILABLE);
        eventPublisher.publishEvent(new CancellationEvent(this));
        return createResponse(HttpStatus.OK, MESSAGE_SUCCESS, null);
    }

    // ========== PRIVATE METHODS ========== //
    private void validateNameAndPhoneNumber(String name, String phone) {
        String nameRegex = "^[가-힣]{2,4}$";
        String phoneRegex = "^01(?:0|1|[6-9])-?(?:\\d{3}|\\d{4})-?\\d{4}$";

        if (!name.matches(nameRegex) || !phone.matches(phoneRegex)) {
            throw new IllegalArgumentException("잘못된 형식의 이름 또는 전화번호입니다.");
        }
    }

    private void checkSeatAvailability(PerformanceSeatInfo seatInfo) {
        if (seatInfo.getIsReserve().equals(OCCUPIED)) {
            throw new IllegalArgumentException("이미 예약된 좌석입니다.");
        }
        if (seatInfo.getIsReserve().equals(ReservationAvailability.DISABLED)) {
            throw new IllegalArgumentException("예약할 수 없는 좌석입니다.");
        }
    }

    private Reservation getReservationEntity(String name, String phoneNumber) {
        return reservationRepository.findByNameAndPhoneNumber(name, phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약 정보를 찾을 수 없습니다."));
    }

    private PerformanceSeatInfo getSeatInfoEntity(RequestReservation requestReservation) {
        UUID id = requestReservation.performanceId();
        int round = requestReservation.round();
        char line = requestReservation.line();
        int seat = requestReservation.seat();

        return seatInfoRepository.findByPerformanceIdAndRoundAndLineAndSeat(id, round, String.valueOf(line), seat)
                .orElseThrow(() -> new IllegalArgumentException("해당 좌석 정보를 찾을 수 없습니다."));
    }

}
