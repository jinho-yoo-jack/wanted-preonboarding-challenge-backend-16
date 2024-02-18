package com.wanted.preonboarding.layered.service.reservation;

import com.wanted.preonboarding.domain.dto.UserDto;
import com.wanted.preonboarding.domain.dto.request.CreateReservationDto;
import com.wanted.preonboarding.domain.dto.reservation.ReserveResponseDto;
import com.wanted.preonboarding.domain.dto.reservation.ReservedListDto;

public interface ReservationService {
  ReservedListDto reservedList(UserDto user);
  ReserveResponseDto createReservation(CreateReservationDto createReservationDto);
  boolean deleteReservation(int id);
}
