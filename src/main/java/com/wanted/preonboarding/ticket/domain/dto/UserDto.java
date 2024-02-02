package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
  private String  userName;
  private String  phoneNumber;
}
