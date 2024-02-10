package com.wanted.preonboarding.ticket.performance;

import com.wanted.preonboarding.ticket.domain.dto.performance.PerformanceDetailDto;
import com.wanted.preonboarding.ticket.domain.dto.performance.PerformanceInfoDto;
import java.util.List;
import java.util.UUID;

public interface PerformanceService {
  List<PerformanceInfoDto> getPerformanceList(String isReserved);
  PerformanceDetailDto getPerformanceDetail(UUID id);
}
