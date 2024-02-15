package com.wanted.preonboarding.domain.dto;

import com.wanted.preonboarding.domain.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
  private String  userName;
  private String  phoneNumber;

  public static UserDto of(UserInfo userInfo) {
    return UserDto.builder()
        .userName(userInfo.getUserName())
        .phoneNumber(userInfo.getPhoneNumber())
        .build();
  }
}
