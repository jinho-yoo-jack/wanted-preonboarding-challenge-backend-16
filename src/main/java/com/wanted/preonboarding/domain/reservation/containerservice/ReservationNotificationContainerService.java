package com.wanted.preonboarding.domain.reservation.containerservice;

import org.springframework.stereotype.Service;

import com.wanted.preonboarding.domain.reservation.domain.request.ReservationNotificationCreateRequest;
import com.wanted.preonboarding.domain.reservation.service.ReservationNotificationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReservationNotificationContainerService {


    private final ReservationNotificationService reservationNotificationService;



    @Transactional
    public void createReservationNotification(
        ReservationNotificationCreateRequest request) {

        this.reservationNotificationService.createReservationNotification(
            request.getPerformanceId(),
            request.getHallSeatId(),
            request.getName(),
            request.getPhoneNumber());
    }




}
