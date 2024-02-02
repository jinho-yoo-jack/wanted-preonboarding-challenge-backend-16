package com.wanted.preonboarding.ticket.domain.dto.reservation;

import com.wanted.preonboarding.ticket.domain.dto.performance.PerformanceSeatDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateReservationDto {
  private String  name;
  private String  phone;
  private long    amount;
  private PerformanceSeatDto seatInfo;
}
