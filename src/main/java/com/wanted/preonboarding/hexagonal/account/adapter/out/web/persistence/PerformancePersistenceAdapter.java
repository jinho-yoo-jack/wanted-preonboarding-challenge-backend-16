package com.wanted.preonboarding.hexagonal.account.adapter.out.web.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.wanted.preonboarding.hexagonal.account.adapter.out.web.persistence.repository.SpringDataPerformanceRepository;
import com.wanted.preonboarding.hexagonal.account.application.port.out.LoadPerformancePort;
import com.wanted.preonboarding.hexagonal.account.domain.PerformanceInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PerformancePersistenceAdapter implements LoadPerformancePort{
	private final SpringDataPerformanceRepository springDataPerformanceRepository;

	@Override
	public List<PerformanceInfo> getAllPerformanceInfo() {
		return springDataPerformanceRepository.findByIsReserve("enable").stream().map(PerformanceInfo::of).toList();
	}
	
	
}
