package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@AllArgsConstructor
@Getter
public class ReserverInfoRequest {
    private String name;
    private String phoneNumber;
}
