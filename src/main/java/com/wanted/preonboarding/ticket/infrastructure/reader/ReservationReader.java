package com.wanted.preonboarding.ticket.infrastructure.reader;

import com.wanted.preonboarding.core.exception.ReservationNotFoundException;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationReader {

    private final ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public Reservation getReservationInquiry(String reservationHolderName, String phoneNumber) {
        return reservationRepository.findByReservationHolderNameAndPhoneNumber(reservationHolderName, phoneNumber).orElseThrow(ReservationNotFoundException::new);
    }
}
