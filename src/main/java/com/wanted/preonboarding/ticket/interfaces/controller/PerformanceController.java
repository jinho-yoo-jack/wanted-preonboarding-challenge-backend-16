package com.wanted.preonboarding.ticket.interfaces.controller;

import com.wanted.preonboarding.common.util.ResponseType;
import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.PerformanceService;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceDTO;
import com.wanted.preonboarding.ticket.interfaces.dto.PerformanceResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
	private final PerformanceService performanceService;

	@GetMapping("/all")
	public ResponseEntity<ResponseHandler<List<PerformanceDTO>>> getAllPerformanceInfoList() {
		return ResponseEntity
			.ok()
			.body(ResponseHandler.<List<PerformanceDTO>>builder()
				.message("예매 가능한 공연/전시를 조회했습니다.")
				.statusCode(HttpStatus.valueOf(ResponseType.SUCCESS.getStatusCode()))
				.data(performanceService.allPerformanceInfoList())
				.build()
			);
	}

	@GetMapping("")
	public ResponseEntity<ResponseHandler<PerformanceResponse>> getPerformanceInfoDetail(
		@RequestParam(value = "id") String id) {
		return ResponseEntity
			.ok()
			.body(ResponseHandler.<PerformanceResponse>builder()
				.message("공연/전시 정보를 조회했습니다.")
				.statusCode(HttpStatus.valueOf(ResponseType.SUCCESS.getStatusCode()))
				.data(performanceService.performanceInfoDetail(UUID.fromString(id)))
				.build()
			);
	}
}
