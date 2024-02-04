package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.ticket.AdminService;
import com.wanted.preonboarding.ticket.domain.dto.request.AddPerformance;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/performance")
    public ResponseEntity<ResponseHandler<PerformanceInfoResponse>> addPerformance(@RequestBody AddPerformance addPerformance)
    {
        return ResponseEntity
            .ok()
            .body(ResponseHandler.<PerformanceInfoResponse>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(adminService.addPerformance(addPerformance))
                .build()
            );
    }
}
