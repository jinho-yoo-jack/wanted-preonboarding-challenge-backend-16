package com.wanted.preonboarding.ticket.infrastructure.store;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationStore {

    private final ReservationRepository reservationRepository;

    @Transactional
    public Reservation store(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
