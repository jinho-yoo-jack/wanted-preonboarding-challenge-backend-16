package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.application.dto.ReservationCancelParam;
import com.wanted.preonboarding.ticket.application.dto.ReservationCreateParam;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.entity.UserInfo;
import com.wanted.preonboarding.ticket.domain.exception.NotFoundException;
import com.wanted.preonboarding.ticket.domain.exception.PaymentException;
import com.wanted.preonboarding.ticket.domain.exception.ForbiddenException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;

    public List<Reservation> getReservations(String userName, String userPhoneNumber) {
        return reservationRepository.findAllByUserInfo(UserInfo.builder().name(userName).phoneNumber(userPhoneNumber).build());
    }

    public Reservation reserve(ReservationCreateParam param) {
        Performance performance = performanceRepository.findById(param.getPerformanceId()).orElseThrow(() -> new NotFoundException("공연이 존재하지 않습니다."));

        performance.reserveSeat(param.getSeatInfo());
        performanceRepository.save(performance);

        Integer price = performance.getPrice();
        // TODO 할인정책에 따른 금액을 구한다.

        if(param.getAmount() < price){
            throw new PaymentException("잔액이 부족합니다.");
        }

        Reservation reservation = Reservation.builder()
                .performanceId(param.getPerformanceId())
                .userInfo(param.getUserInfo())
                .seatInfo(param.getSeatInfo())
                .build();
        return reservationRepository.save(reservation);
    }

    public void cancel(ReservationCancelParam param) {
        Reservation reservation = reservationRepository.findById(param.getReservationId()).orElseThrow(() -> new NotFoundException("예약이 존재하지 않습니다."));

        if(!reservation.compareUserInfo(param.getUserInfo())){
            throw new ForbiddenException("예약 취소 권한이 없습니다.");
        }
        reservationRepository.delete(reservation);

        Performance performance = performanceRepository.findById(reservation.getPerformanceId()).orElseThrow(() -> new NotFoundException("공연이 존재하지 않습니다."));
        performance.cancelSeat(reservation.getSeatInfo());
        performanceRepository.save(performance);
    }
}
