package com.wanted.preonboarding.ticket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckReserveRequest {
    private String name;
    private String phoneNumber;


}
