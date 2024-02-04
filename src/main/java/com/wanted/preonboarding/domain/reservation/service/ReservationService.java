package com.wanted.preonboarding.domain.reservation.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.domain.performance.domain.entity.Performance;
import com.wanted.preonboarding.domain.performance.domain.entity.PerformanceHallSeat;
import com.wanted.preonboarding.domain.reservation.domain.entity.Reservation;
import com.wanted.preonboarding.domain.reservation.repository.ReservationRepository;
import com.wanted.preonboarding.domain.reservation.repository.ReservationRepositoryQueryDsl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationRepositoryQueryDsl reservationRepositoryQueryDsl;


    @Transactional
    public Reservation createReservation(
        Performance performance,
        PerformanceHallSeat hallSeat,
        String name,
        String phoneNumber) {

        Reservation reservation = Reservation.of(performance, hallSeat, name, phoneNumber);

        this.reservationRepository.save(reservation);

        return reservation;
    }

    @Transactional
    public void deleteReservation(
        Reservation reservation) {

        this.reservationRepository.delete(reservation);
    }


    @Transactional(readOnly = true)
    public Page<Reservation> getReservationPage(
        String name,
        String phoneNumber,
        int page, int size) {

        return this.reservationRepositoryQueryDsl.findPage(name, phoneNumber, page, size);
    }

    @Transactional(readOnly = true)
    public Reservation getReservation(Long reservationId){

        return this.reservationRepository.findById(reservationId)
            .orElseThrow();
    }


    @Transactional(readOnly = true)
    public boolean isExistsReservation(
        Performance performance,
        PerformanceHallSeat hallSeat) {

        return this.reservationRepository.existsByPerformanceIdAndHallSeatId(performance.getId(), hallSeat.getId());
    }

}
