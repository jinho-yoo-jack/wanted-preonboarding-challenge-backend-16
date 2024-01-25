package com.wanted.preonboarding.performance.dto.request;

import com.wanted.preonboarding.performance.domain.constant.ReserveStatus;
import com.wanted.preonboarding.performance.dto.PerformanceSearchParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerformanceSearchRequest {
    private String reserveStatus;

    public PerformanceSearchParam toDto() {
        return PerformanceSearchParam.builder()
                .reserveStatus(ReserveStatus.valueOf(reserveStatus))
                .build();
    }
}
