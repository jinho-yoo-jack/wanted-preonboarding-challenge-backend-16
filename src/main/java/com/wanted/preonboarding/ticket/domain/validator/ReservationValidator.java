package com.wanted.preonboarding.ticket.domain.validator;

import com.wanted.preonboarding.ticket.interfaces.dto.ReservationReadDto;

public interface ReservationValidator {

    int getOrder();

    void validate(ReservationReadDto reservationReadDto);
}
