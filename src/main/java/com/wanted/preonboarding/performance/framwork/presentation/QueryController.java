package com.wanted.preonboarding.performance.framwork.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.performance.application.PerformService;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformanceResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("query")
@RequiredArgsConstructor
@Slf4j
public class QueryController {
    private final PerformService performService;

    @GetMapping("/all/performance")
    public ResponseEntity<ResponseHandler<List<PerformanceResponse>>> getAllPerformanceInfoList(
        @RequestParam(value = "isReserve") boolean isReserve
    ) {
        return ResponseEntity
            .ok()
            .body(ResponseHandler.<List<PerformanceResponse>>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(performService.getAllPerformanceInfoList(isReserve))
                .build()
            );
    }

    @GetMapping("/performance")
    public ResponseEntity<ResponseHandler<PerformanceResponse>> getPerformanceInfoDetail(
        @RequestParam(value = "id") String id
    ) {
        return ResponseEntity
            .ok()
            .body(ResponseHandler.<PerformanceResponse>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(performService.getPerformanceInfoDetail(UUID.fromString(id)))
                .build()
            );
    }
}
