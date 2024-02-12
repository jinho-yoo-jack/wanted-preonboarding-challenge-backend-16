package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.application.discount.DiscountPolicy;
import com.wanted.preonboarding.ticket.application.dto.ReservationCancelParam;
import com.wanted.preonboarding.ticket.application.dto.ReservationCreateParam;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.entity.UserInfo;
import com.wanted.preonboarding.ticket.domain.event.ReservationCanceledEvent;
import com.wanted.preonboarding.ticket.domain.exception.NotFoundException;
import com.wanted.preonboarding.ticket.domain.exception.PaymentException;
import com.wanted.preonboarding.ticket.domain.exception.ForbiddenException;
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
        Performance performance = performanceRepository.findById(param.getPerformanceId()).orElseThrow(() -> new NotFoundException("공연이 존재하지 않습니다."));

        performance.reserveSeat(param.getSeatInfo());
        performanceRepository.save(performance);

        Integer price = performance.getPrice();
        Integer discountedPrice = discountPolicy.calculateDiscountedPrice(price,performance);
        System.out.println("정가: " + price);
        System.out.println("할인가: " + discountedPrice);

        if(param.getAmount() < discountedPrice){
            throw new PaymentException("잔액이 부족합니다.");
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
        Reservation reservation = reservationRepository.findById(param.getReservationId()).orElseThrow(() -> new NotFoundException("예약이 존재하지 않습니다."));

        if(!reservation.getUserInfo().equals(param.getUserInfo())){
            throw new ForbiddenException("예약 취소 권한이 없습니다.");
        }
        reservationRepository.delete(reservation);

        Performance performance = performanceRepository.findById(reservation.getPerformanceId()).orElseThrow(() -> new NotFoundException("공연이 존재하지 않습니다."));
        performance.cancelSeat(reservation.getSeatInfo());
        performanceRepository.save(performance);

        eventPublisher.publishEvent(ReservationCanceledEvent.builder()
                .performanceId(performance.getId())
                .performanceName(performance.getName())
                .seatInfo(reservation.getSeatInfo())
                .build());
    }
}
