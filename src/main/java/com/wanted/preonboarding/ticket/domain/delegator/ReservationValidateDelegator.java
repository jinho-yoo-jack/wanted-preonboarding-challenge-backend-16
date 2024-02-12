package com.wanted.preonboarding.ticket.domain.delegator;

import com.wanted.preonboarding.ticket.domain.validator.ReservationValidator;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationValidateDelegator {

    private final List<ReservationValidator> validators;

    public void validate(ReservationReadDto reservationReadDto) {
        validators.stream()
                .sorted(Comparator.comparingInt(ReservationValidator::getOrder))
                .forEach(x -> x.validate(reservationReadDto));
    }
}
