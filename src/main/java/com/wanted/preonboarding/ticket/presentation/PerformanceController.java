package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.PerformanceService;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceFindResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/performance")
public class PerformanceController {
    private final PerformanceService performanceService;

    @GetMapping("/")
    public ResponseEntity<ResponseHandler<List<PerformanceFindResponse>>> findAllPerformanceInfo(@NotBlank(message = "예매 가능 여부를 입력해주세요.") String isReserve) {
        List<PerformanceFindResponse> findPerformancesList = performanceService.findPerformances(isReserve);
        return ResponseEntity.ok()
                .body(ResponseHandler.<List<PerformanceFindResponse>>builder()
                        .statusCode(HttpStatus.OK)
                        .message("Success")
                        .data(findPerformancesList)
                        .build()
                );
    }

}
