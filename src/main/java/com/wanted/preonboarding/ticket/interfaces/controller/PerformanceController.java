package com.wanted.preonboarding.ticket.interfaces.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.PerformanceService;
import com.wanted.preonboarding.ticket.domain.info.PerformanceInfo;
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
	public ResponseEntity<ResponseHandler<List<PerformanceInfo>>> getAllPerformanceInfoList() {
		System.out.println("getAllPerformanceInfoList");
		return ResponseEntity
			.ok()
			.body(ResponseHandler.<List<PerformanceInfo>>builder()
				.message("Success")
				.statusCode(HttpStatus.OK)
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
				.message("Success")
				.statusCode(HttpStatus.OK)
				.data(performanceService.performanceInfoDetail(UUID.fromString(id)))
				.build()
			);
	}
}
