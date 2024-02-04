package com.wanted.preonboarding.ticket.application.reserve;

import com.wanted.preonboarding.ticket.application.discount.DiscountManager;
import com.wanted.preonboarding.ticket.domain.performance.Performance;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceRepository;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.reservation.Reservation;
import com.wanted.preonboarding.ticket.domain.reservation.ReservationRepository;
import com.wanted.preonboarding.ticket.dto.request.discount.PaymentInfo;
import com.wanted.preonboarding.ticket.dto.request.notification.ReservationCancelEvent;
import com.wanted.preonboarding.ticket.dto.request.reservation.ReservationRequest;
import com.wanted.preonboarding.ticket.dto.response.page.PageResponse;
import com.wanted.preonboarding.ticket.dto.response.reservation.ReservationInfo;
import com.wanted.preonboarding.ticket.dto.result.CancelReservationInfo;
import com.wanted.preonboarding.ticket.dto.result.ReservationModel;
import com.wanted.preonboarding.ticket.exception.argument.InvalidSeatException;
import com.wanted.preonboarding.ticket.exception.badrequest.AmountNotEnoughException;
import com.wanted.preonboarding.ticket.exception.notfound.PerformanceNotFoundException;
import com.wanted.preonboarding.ticket.exception.notfound.ReservationNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReserveServiceImpl implements ReserveService {

    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;
    private final DiscountManager discountManager;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public ReservationInfo reserve(final ReservationRequest request, final LocalDateTime requestTime) {
        // 예약 가능한 좌석인지 확인
        PerformanceSeatInfo seatInfo = findSeatInfo(request);
        Performance performance = findPerformance(request.performanceId());

        // 할인 적용
        PaymentInfo paymentInfo = PaymentInfo.of(performance.getPrice(), request, requestTime);
        int discountedPrice = (int) discountManager.applyDiscount(paymentInfo);
        validateReservation(seatInfo, request, discountedPrice);

        // 에약 정보 저장
        Reservation reservation = createReservation(seatInfo, request.name(), request.phone());
        reservationRepository.save(reservation);

        seatInfo.reserve();
        return ReservationInfo.of(reservation, seatInfo, performance.getName(), discountedPrice);
    }

    @Override
    public PageResponse<ReservationModel> findReservation(final String name, final String phone, final Pageable pageable) {
        Page<ReservationModel> reservationModel = reservationRepository.findReservationModel(name, phone, pageable);
        return PageResponse.of(reservationModel);
    }

    @Transactional
    @Override
    public CancelReservationInfo cancel(final int reservationId) {
        // 취소할 예약 정보 조회
        CancelReservationInfo cancelInfo = reservationRepository.findInfoForCancel(reservationId)
            .orElseThrow(ReservationNotFoundException::new);

        // 예약 정보 삭제
        reservationRepository.deleteById(reservationId);

        // 이벤트 발행 (알림 전송)
        eventPublisher.publishEvent(new ReservationCancelEvent(cancelInfo));
        return cancelInfo;
    }

    private Reservation createReservation(final PerformanceSeatInfo seatInfo, final String name, final String phoneNumber) {
        return Reservation.builder()
            .performanceId(seatInfo.getPerformanceId())
            .name(name)
            .phoneNumber(phoneNumber)
            .round(seatInfo.getRound())
            .gate(seatInfo.getGate())
            .line(seatInfo.getLine())
            .seat(seatInfo.getSeat())
            .build();
    }

    private void validateReservation(final PerformanceSeatInfo seatInfo, final ReservationRequest request, final int discountedPrice) {
        seatInfo.validatedReservation();
        if (request.costAmount() < discountedPrice) throw new AmountNotEnoughException();
    }

    private Performance findPerformance(final String performanceId) {
        UUID id = UUID.fromString(performanceId);

        return performanceRepository.findById(id)
            .orElseThrow(PerformanceNotFoundException::new);
    }

    private PerformanceSeatInfo findSeatInfo(final ReservationRequest request) {
        UUID performanceId = UUID.fromString(request.performanceId());

        return performanceRepository.findSeatByIdAndInfo(performanceId, request.round(), request.line(), request.seat())
            .orElseThrow(InvalidSeatException::new);
    }
}
