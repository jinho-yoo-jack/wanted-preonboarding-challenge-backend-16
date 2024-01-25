package com.wanted.preonboarding.ticket.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.base.rsData.RsData;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceService {
	private final PerformanceRepository performanceRepository;

	/*
		기능 3 : 공연 및 전시 정보 조회(목록, 상세 조회)
			- Request Message: 예매 가능 여부
			- Response Message: 예매 가능한 공연 리스트(정보: 공연명, 회차, 시작 일시, 예매 가능 여부)
	 */
	public RsData getList(String status, int page) {
		PageRequest pageable = PageRequest.of(page, 5);
		Page<Performance> result = performanceRepository.findByIsReserve(status, pageable);

		String msg = status.equals("enable") ? "예매 가능한 공연" : "예매 불가능한 공연";

		if (result.isEmpty()) {
			return RsData.of("F-1", msg + "이 없습니다.");
		}

		return RsData.of("S-1", msg + " 목록 조회 성공", result);
	}
}
