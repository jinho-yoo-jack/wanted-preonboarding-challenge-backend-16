package com.wanted.preonboarding.ticket.application.reserve;

import com.wanted.preonboarding.ticket.dto.request.reservation.ReservationRequest;
import com.wanted.preonboarding.ticket.dto.response.page.PageResponse;
import com.wanted.preonboarding.ticket.dto.response.reservation.ReservationInfo;
import com.wanted.preonboarding.ticket.dto.result.CancelReservationInfo;
import com.wanted.preonboarding.ticket.dto.result.ReservationModel;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;

public interface ReserveService {
    ReservationInfo reserve(ReservationRequest request, LocalDateTime requestTime);
    PageResponse<ReservationModel> findReservation(String name, String phone, Pageable pageable);
    CancelReservationInfo cancel(int reservationId);
}
