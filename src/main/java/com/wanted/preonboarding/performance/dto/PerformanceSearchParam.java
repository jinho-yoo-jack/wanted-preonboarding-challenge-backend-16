package com.wanted.preonboarding.performance.dto;

import com.wanted.preonboarding.performance.domain.constant.ReserveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PerformanceSearchParam {
    private ReserveStatus reserveStatus;
}
