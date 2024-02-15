package com.wanted.preonboarding.layered.service.reservation;

import com.wanted.preonboarding.domain.dto.UserDto;
import com.wanted.preonboarding.domain.dto.reservation.CreateReservationDto;
import com.wanted.preonboarding.domain.dto.reservation.ReserveResponseDto;
import com.wanted.preonboarding.domain.dto.reservation.ReservedListDto;
import com.wanted.preonboarding.layered.service.discount.DiscountPolicy;

public interface ReservationService {
  ReservedListDto reservedList(UserDto user);
  ReserveResponseDto createReservation(CreateReservationDto createReservationDto, DiscountPolicy policy);
  boolean deleteReservation(int id);
}
