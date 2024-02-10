package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.dto.request.FindReserveRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.FindReserveResponse;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationResponse;

public interface ReservationRepositoryCustom {
    FindReserveResponse findReserveInfo(FindReserveRequest request);
    ReservationResponse cancelReservation(ReservationRequest request);
}
