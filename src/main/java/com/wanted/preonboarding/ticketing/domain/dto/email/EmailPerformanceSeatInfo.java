package com.wanted.preonboarding.ticketing.domain.dto.email;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailPerformanceSeatInfo {
    private final Long id;
    private final int gate;
    private final String line;
    private final int seat;
}
