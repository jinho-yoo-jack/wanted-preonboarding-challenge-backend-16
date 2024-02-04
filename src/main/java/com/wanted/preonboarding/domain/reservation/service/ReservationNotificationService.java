package com.wanted.preonboarding.domain.reservation.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.wanted.preonboarding.domain.reservation.domain.entity.ReservationNotification;
import com.wanted.preonboarding.domain.reservation.repository.ReservationNotificationRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReservationNotificationService {

    private final ReservationNotificationRepository reservationNotificationRepository;


    @Transactional
    public void createReservationNotification(
        UUID performanceId,
        Long hallSeatId,

        String name,
        String phoneNumber) {

        this.reservationNotificationRepository.save(
            ReservationNotification.of(
                performanceId,
                hallSeatId,

                name,
                phoneNumber));
    }

    @Transactional
    public void sendReservationNotification(
        UUID performanceId,
        Long hallSeatId) {

        // TODO send
    }



}
