package com.wanted.preonboarding.ticket.support;

import com.wanted.preonboarding.ticket.application.dto.request.FindReserveServiceRequest;

public interface ReservationRequestFactory {

    static FindReserveServiceRequest createFindRequest() {
        return FindReserveServiceRequest.builder()
                .reservationName("원티드")
                .reservationPhoneNumber("010-1112-2223")
                .build();
    }
}
