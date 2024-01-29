package com.wanted.preonboarding.ticket.application.reserve;

import com.wanted.preonboarding.ticket.dto.request.reservation.ReservationRequest;
import com.wanted.preonboarding.ticket.dto.response.reservation.ReservationInfo;
import java.time.LocalDateTime;

public interface ReserveService {
    ReservationInfo reserve(ReservationRequest request, LocalDateTime requestTime);
}
