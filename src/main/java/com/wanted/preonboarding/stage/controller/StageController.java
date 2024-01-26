package com.wanted.preonboarding.stage.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.stage.dto.request.StageRequest;
import com.wanted.preonboarding.stage.dto.response.StageResponse;
import com.wanted.preonboarding.stage.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stage")
public class StageController {
    private final StageService stageService;

    @PostMapping
    public ResponseEntity<ResponseHandler<StageResponse>> createStage(@RequestBody StageRequest request) {
        return ResponseEntity.ok(ResponseHandler.<StageResponse>builder()
                .statusCode(HttpStatus.OK)
                .message("Success")
                .data(StageResponse.of(stageService.createStage(request.toDto())))
                .build()
        );
    }
}
