package com.wanted.preonboarding.ticket.domain.dto.request;

import java.util.UUID;

public record RequestReservation(
        String name,
        String phoneNumber,
        Integer balance,
        UUID performanceId,
        Integer round,
        Character line,
        Integer seat
) {


}
