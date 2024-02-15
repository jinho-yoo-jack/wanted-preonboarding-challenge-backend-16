package com.wanted.preonboarding.ticket.domain.dto.reservation;

import com.wanted.preonboarding.ticket.domain.dto.UserDto;
import com.wanted.preonboarding.ticket.domain.dto.performance.PerformanceSeatDto;
import com.wanted.preonboarding.ticket.domain.entity.UserInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ReservedListDto {
  private UserDto user;
  private List<PerformanceSeatDto> reserved;

  public static ReservedListDto of(UserInfo entity) {
    return ReservedListDto.builder()
        .user(UserDto.of(entity))
        .reserved(entity.getReservations().stream()
            .map(res -> PerformanceSeatDto.of(res.getSeatInfo()))
            .toList()
        )
        .build();
  }
}
