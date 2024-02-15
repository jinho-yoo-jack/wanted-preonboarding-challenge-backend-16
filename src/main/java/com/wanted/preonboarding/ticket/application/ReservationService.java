package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.core.domain.exception.CustomException;
import com.wanted.preonboarding.ticket.application.discount.DiscountPolicy;
import com.wanted.preonboarding.ticket.application.dto.ReservationCancelParam;
import com.wanted.preonboarding.ticket.application.dto.ReservationCreateParam;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.entity.UserInfo;
import com.wanted.preonboarding.ticket.domain.event.ReservationCanceledEvent;
import com.wanted.preonboarding.ticket.domain.exception.TicketErrorCode;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final DiscountPolicy discountPolicy;

    @Transactional(readOnly = true)
    public List<Reservation> getReservations(final String userName, final String userPhoneNumber) {
        return reservationRepository.findAllByUserInfo(UserInfo.builder().name(userName).phoneNumber(userPhoneNumber).build());
    }

    @Transactional
    public Reservation reserve(final ReservationCreateParam param) {
        Performance performance = performanceRepository.findById(param.getPerformanceId()).orElseThrow(() -> new CustomException(TicketErrorCode.PERFORMANCE_NOT_FOUND));

        performance.reserveSeat(param.getSeatInfo());
        performanceRepository.save(performance);

        Integer price = performance.getPrice();
        Integer discountedPrice = discountPolicy.calculateDiscountedPrice(price,performance);
        if(param.getAmount() < discountedPrice){
            throw new CustomException(TicketErrorCode.BALANCE_INSUFFICIENT);
        }

        Reservation reservation = Reservation.builder()
                .performanceId(param.getPerformanceId())
                .userInfo(param.getUserInfo())
                .seatInfo(param.getSeatInfo())
                .build();
        return reservationRepository.save(reservation);
    }

    @Transactional
    public void cancel(final ReservationCancelParam param) {
        Reservation reservation = reservationRepository.findById(param.getReservationId()).orElseThrow(() -> new CustomException(TicketErrorCode.RESERVATION_NOT_FOUND));

        if(!reservation.getUserInfo().equals(param.getUserInfo())){
            throw new CustomException(TicketErrorCode.NOT_RESERVATION_OWNER);
        }
        reservationRepository.delete(reservation);

        Performance performance = performanceRepository.findById(reservation.getPerformanceId()).orElseThrow(() -> new CustomException(TicketErrorCode.PERFORMANCE_NOT_FOUND));

        performance.cancelSeat(reservation.getSeatInfo());
        performanceRepository.save(performance);

        eventPublisher.publishEvent(ReservationCanceledEvent.builder()
                .performanceId(performance.getId())
                .performanceName(performance.getName())
                .seatInfo(reservation.getSeatInfo())
                .build());
    }
}
