package com.wanted.preonboarding.ticket.performance;

import com.wanted.preonboarding.ticket.domain.dto.performance.PerformanceDetailDto;
import com.wanted.preonboarding.ticket.domain.dto.performance.PerformanceInfoDto;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerformanceServiceV1 implements PerformanceService {
  final private PerformanceRepository repository;

  @Autowired
  PerformanceServiceV1(PerformanceRepository repository) {
    this.repository = repository;
  }

  public List<PerformanceInfoDto> getPerformanceList(String isReserved) {
    return this.repository.findByIsReserve(isReserved).stream()
        .map(PerformanceInfoDto::of).collect(Collectors.toList());
  }
  public PerformanceDetailDto getPerformanceDetail(UUID id) {
    return this.repository.findById(id).map(PerformanceDetailDto::of).get();
  }
}