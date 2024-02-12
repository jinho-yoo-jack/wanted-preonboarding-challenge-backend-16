package com.wanted.preonboarding.ticket.application.dto;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PerformanceSearchCondition {
    private String name;
    private Integer round;
    private Integer minPrice;
    private Integer maxPrice;
    private PerformanceType type;
    private Boolean isReservable;
}
