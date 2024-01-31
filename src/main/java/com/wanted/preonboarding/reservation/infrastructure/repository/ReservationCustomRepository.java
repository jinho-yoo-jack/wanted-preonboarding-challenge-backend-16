package com.wanted.preonboarding.reservation.infrastructure.repository;

import com.wanted.preonboarding.reservation.application.dto.ReservationResponse;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;

import java.util.List;
import java.util.Optional;

public interface ReservationCustomRepository {

    List<ReservationResponse> findReservationResponseByUserInfo(final UserInfo userInfo);

    Optional<ReservationResponse> findReservationResponseById(int reservationId);
}
