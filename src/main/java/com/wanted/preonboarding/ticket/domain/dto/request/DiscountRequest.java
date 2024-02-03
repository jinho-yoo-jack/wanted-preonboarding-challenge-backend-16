package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class DiscountRequest {
    private int performancePrice;
    private LocalDateTime reserveDate;
    private LocalDateTime performanceDateTime;
}
