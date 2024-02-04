package com.wanted.preonboarding.domain.performance.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.domain.common.domain.response.ResponseHandler;
import com.wanted.preonboarding.domain.common.utils.ResponseUtils;
import com.wanted.preonboarding.domain.performance.containerservice.PerformanceContainerService;
import com.wanted.preonboarding.domain.performance.domain.response.PerformanceDetailResponse;
import com.wanted.preonboarding.domain.performance.domain.response.PerformancePageResponse;
import com.wanted.preonboarding.domain.performance.domain.enums.PerformanceStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/performance")
@RestController
public class PerformanceController {

	private final PerformanceContainerService performanceContainerService;

	@GetMapping(value = "")
	public ResponseEntity<ResponseHandler<PerformancePageResponse>> getPerformancePage(
		@RequestParam(required = false) List<PerformanceStatus> status,
		@RequestParam(required = false, defaultValue = "0") int page,
		@RequestParam(required = false, defaultValue = "20") int size) {

		return ResponseUtils.resultOk(
			this.performanceContainerService.getPerformancePage(status, page, size));
	}

    @GetMapping(value = "/{performanceId}")
    public ResponseEntity<ResponseHandler<PerformanceDetailResponse>> getPerformanceDetail(
        @PathVariable UUID performanceId) {

        return ResponseUtils.resultOk(
			this.performanceContainerService.getPerformanceDetail(performanceId));
    }





}
