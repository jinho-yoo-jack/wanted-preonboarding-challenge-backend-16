package com.wanted.preonboarding.domain.dto.reservation;

import com.wanted.preonboarding.domain.dto.performance.PerformanceSeatDto;
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
