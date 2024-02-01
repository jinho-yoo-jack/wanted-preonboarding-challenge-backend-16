package com.wanted.preonboarding.ticket.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class GetAlarmRequestDto {
    private String memberName;
}
