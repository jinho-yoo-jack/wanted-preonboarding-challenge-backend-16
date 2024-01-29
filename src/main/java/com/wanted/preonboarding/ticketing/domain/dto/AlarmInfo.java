package com.wanted.preonboarding.ticketing.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlarmInfo {
    private final String email;
    private final String name;
    private final String phoneNumber;
}
