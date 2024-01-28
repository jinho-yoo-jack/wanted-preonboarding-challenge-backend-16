package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ApiResponse;
import com.wanted.preonboarding.ticket.application.PerformanceService;
import com.wanted.preonboarding.ticket.application.dto.response.PerformanceResponse;
import com.wanted.preonboarding.ticket.presentation.dto.request.AwaitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/performances")
@RestController
public class PerformanceController {

    public static final String AWAIT_MESSAGE_FORMAT = "[%s] 예약 알림이 등록되었습니다.";
    private static final String FIND_PERFORMANCE_DEFAULT = "enable";

    private final PerformanceService performanceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PerformanceResponse>>> findPerformances(
            @RequestParam(defaultValue = FIND_PERFORMANCE_DEFAULT) String isReserve
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        performanceService.findPerformances(isReserve)
                )
        );
    }

    @GetMapping("/{performanceId}")
    public ResponseEntity<ApiResponse<PerformanceResponse>> findOne(@PathVariable UUID performanceId) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        performanceService.findOne(performanceId)
                )
        );
    }

    @PostMapping("/{performanceId}/await")
    public ResponseEntity<ApiResponse<String>> await(@PathVariable UUID performanceId,
                                                     @RequestBody @Validated AwaitRequest request
    ) {
        performanceService.await(performanceId, request.userId());
        return ResponseEntity.ok(ApiResponse.ok(String.format(AWAIT_MESSAGE_FORMAT, performanceId)));
    }

}
