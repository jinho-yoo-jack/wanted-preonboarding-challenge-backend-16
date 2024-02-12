package com.wanted.preonboarding.ticket.domain.validator;

import com.wanted.preonboarding.core.exception.SeatAlreadyReservedException;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class SeatReservationValidator implements ReservationValidator {
    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public void validate(ReservationReadDto reservationReadDto) {
        if (reservationReadDto.getPerformanceSeatInfo().getIsReserve().isDisable()) {
            throw new SeatAlreadyReservedException();
        }
    }
}
