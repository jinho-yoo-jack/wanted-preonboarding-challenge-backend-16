package com.wanted.preonboarding.ticket.domain.dto.reservation;

import com.wanted.preonboarding.ticket.domain.dto.UserDto;
import com.wanted.preonboarding.ticket.domain.dto.performance.PerformanceSeatDto;
import com.wanted.preonboarding.ticket.domain.entity.UserInfo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
