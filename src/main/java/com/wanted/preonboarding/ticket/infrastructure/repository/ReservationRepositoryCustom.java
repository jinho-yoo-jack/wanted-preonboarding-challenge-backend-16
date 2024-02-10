package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.dto.request.FindReserveRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.FindReserveResponse;

public interface ReservationRepositoryCustom {
    FindReserveResponse findReserveInfo(FindReserveRequest request);
}
