package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.application.dto.ReservationCreateParam;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.exception.NotFoundException;
import com.wanted.preonboarding.ticket.domain.exception.PaymentException;
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
        return reservationRepository.findAllByUserInfoNameAndUserInfoPhoneNumber(userName, userPhoneNumber);
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
        reservationRepository.save(reservation);

        return reservation;
    }
}
