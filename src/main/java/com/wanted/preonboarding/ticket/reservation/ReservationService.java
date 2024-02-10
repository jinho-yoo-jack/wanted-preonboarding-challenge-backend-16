package com.wanted.preonboarding.ticket.reservation;

import com.wanted.preonboarding.ticket.domain.dto.UserDto;
import com.wanted.preonboarding.ticket.domain.dto.reservation.CreateReservationDto;
import com.wanted.preonboarding.ticket.domain.dto.reservation.ReserveResponseDto;
import com.wanted.preonboarding.ticket.domain.dto.reservation.ReservedListDto;
import com.wanted.preonboarding.ticket.reservation.discount.DiscountPolicy;

public interface ReservationService {
  ReservedListDto reservedList(UserDto user);
  ReserveResponseDto createReservation(CreateReservationDto createReservationDto, DiscountPolicy policy);
  boolean deleteReservation(int id);
}
