package com.wanted.preonboarding.performance.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.performance.application.PerformanceService;
import com.wanted.preonboarding.performance.domain.dto.PerformanceGroupOrder;
import com.wanted.preonboarding.performance.domain.dto.PerformanceInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;

    /**
     * 예약 가능한 공연 및 전시 정보 호출
     * @return
     */
    @PostMapping("/select/list")
    public ResponseEntity<List<PerformanceInfo>> selectEnablePerformanceInfoList(){
        return ResponseEntity.ok(performanceService.selectEnablePerformanceInfoList());
    }
    
    /**
     * 예약 가능한 공연 및 전시 정보 호출
     * @return
     */
    @PostMapping("/select/detail")
    public ResponseEntity<List<PerformanceInfo>> selectEnablePerformanceInfoDetail(@RequestBody @Validated(PerformanceGroupOrder.selectEnablePerformanceInfoDetail.class) PerformanceInfo performanceInfo){
        return ResponseEntity.ok(performanceService.selectEnablePerformanceInfoDetail(performanceInfo));
    }
}
