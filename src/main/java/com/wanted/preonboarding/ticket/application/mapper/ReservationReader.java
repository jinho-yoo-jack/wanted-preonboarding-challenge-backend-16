package com.wanted.preonboarding.ticket.application.mapper;

import com.wanted.preonboarding.ticket.infrastructure.dto.ReserveQueryResponse;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class ReservationReader {

    private final ReservationRepository reservationRepository;

    public List<ReserveQueryResponse> findReservationInfoOfUser(String name, String phoneNumber) {
        return reservationRepository.findByNameAndPhoneNumber(name, phoneNumber);
    }
}
