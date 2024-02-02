package com.wanted.preonboarding.ticket.domain.dto.reservation;

import com.wanted.preonboarding.ticket.domain.dto.UserDto;
import com.wanted.preonboarding.ticket.domain.dto.performance.PerformanceSeatDto;
import java.util.List;
import lombok.Data;

@Data
public class ReservedListDto {
  private UserDto user;
  private List<PerformanceSeatDto> reserved;
}
