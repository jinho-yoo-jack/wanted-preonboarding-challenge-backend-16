package com.wanted.preonboarding.ticket.domain.reservation;

import com.wanted.preonboarding.ticket.dto.result.CancelReservationInfo;
import com.wanted.preonboarding.ticket.dto.result.ReservationModel;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationSearchRepository {

    // 예약 정보 조회
    Page<ReservationModel> findReservationModel(String name, String phoneNumber, Pageable pageable);
    Optional<CancelReservationInfo> findInfoForCancel(int reservationId);
}
