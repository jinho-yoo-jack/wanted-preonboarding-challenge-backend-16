package com.wanted.preonboarding.ticket.presentation;

import static org.springframework.data.domain.Sort.Direction.DESC;

import com.wanted.preonboarding.ticket.application.performance.PerformanceService;
import com.wanted.preonboarding.ticket.dto.response.page.PageResponse;
import com.wanted.preonboarding.ticket.dto.response.performance.PerformanceInfo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/app/performance")
@RestController
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping("/{performanceId}")
    public ResponseEntity<PerformanceInfo> findPerformance(@PathVariable String performanceId) {
        PerformanceInfo performanceInfo = performanceService.findPerformance(UUID.fromString(performanceId));
        return ResponseEntity.ok(performanceInfo);
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<PerformanceInfo>> finPerformance(@RequestParam(name = "reserveState") String reserveState,
                                                                    @PageableDefault(sort = "startDate", direction = DESC) Pageable pageable) {
        PageResponse<PerformanceInfo> performanceAll = performanceService.findPerformanceAll(reserveState, pageable);
        return ResponseEntity.ok(performanceAll);
    }

}
