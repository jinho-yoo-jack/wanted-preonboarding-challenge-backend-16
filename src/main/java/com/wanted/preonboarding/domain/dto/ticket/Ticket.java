package com.wanted.preonboarding.domain.dto.ticket;

import com.wanted.preonboarding.domain.dto.UserDto;
import com.wanted.preonboarding.domain.dto.performance.PerformanceSeatDto;

public record Ticket(
    UserDto user,
    PerformanceSeatDto seat,
    int price
) {}
