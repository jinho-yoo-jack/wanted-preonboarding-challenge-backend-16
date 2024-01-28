package com.wanted.preonboarding.ticket.application.mapper;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.dto.ReserveQueryResponse;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class ReservationReader {

    public static final String RESERVATION_NOT_FOUND_MESSAGE_FORMAT = "[%d] 예약을 찾을 수 없습니다.";

    private final ReservationRepository reservationRepository;

    public List<ReserveQueryResponse> findReservationInfoOfUser(String name, String phoneNumber) {
        return reservationRepository.findByNameAndPhoneNumber(name, phoneNumber);
    }

    public Reservation findById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(RESERVATION_NOT_FOUND_MESSAGE_FORMAT, reservationId))
                );
    }
}
