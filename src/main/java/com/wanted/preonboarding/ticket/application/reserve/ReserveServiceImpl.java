package com.wanted.preonboarding.ticket.application.reserve;

import static com.wanted.preonboarding.ticket.domain.performance.model.ReserveState.RESERVED;

import com.wanted.preonboarding.ticket.application.discount.DiscountManager;
import com.wanted.preonboarding.ticket.domain.performance.Performance;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceRepository;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.reservation.Reservation;
import com.wanted.preonboarding.ticket.domain.reservation.ReservationRepository;
import com.wanted.preonboarding.ticket.dto.request.discount.PaymentInfo;
import com.wanted.preonboarding.ticket.dto.request.reservation.ReservationRequest;
import com.wanted.preonboarding.ticket.dto.response.reservation.ReservationInfo;
import com.wanted.preonboarding.ticket.exception.argument.InvalidSeatException;
import com.wanted.preonboarding.ticket.exception.badrequest.AmountNotEnoughException;
import com.wanted.preonboarding.ticket.exception.badrequest.SeatAlreadyReservedException;
import com.wanted.preonboarding.ticket.exception.notfound.PerformanceNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReserveServiceImpl implements ReserveService {

    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;
    private final DiscountManager discountManager;

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

    private void validateReservation(final PerformanceSeatInfo seatInfo, final ReservationRequest request, int discountedPrice) {
        if (seatInfo.getIsReserved() == RESERVED) throw new SeatAlreadyReservedException();
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
