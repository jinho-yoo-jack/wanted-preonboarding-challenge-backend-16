package com.wanted.preonboarding.layered.service.performance;

import com.wanted.preonboarding.domain.dto.performance.PerformanceDetailDto;
import com.wanted.preonboarding.layered.repository.PerformanceRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerformanceServiceV1 implements PerformanceService {
  final private PerformanceRepository repository;

  @Autowired
  PerformanceServiceV1(PerformanceRepository repository) {
    this.repository = repository;
  }

  public List<PerformanceDetailDto> getPerformanceList(String isReserved) {
    return this.repository.findByIsReserve(isReserved).stream()
        .map(PerformanceDetailDto::of).toList();
  }
  public PerformanceDetailDto getPerformanceDetail(UUID id) {
    return this.repository.findById(id).map(PerformanceDetailDto::of).get();
  }
}