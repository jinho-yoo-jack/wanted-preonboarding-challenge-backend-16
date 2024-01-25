package com.wanted.preonboarding.performance.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.performance.dto.request.PerformanceRequest;
import com.wanted.preonboarding.performance.dto.request.PerformanceSearchRequest;
import com.wanted.preonboarding.performance.dto.response.PerformanceResponse;
import com.wanted.preonboarding.performance.dto.response.PerformanceSearchResponse;
import com.wanted.preonboarding.performance.service.PerformanceService;
import com.wanted.preonboarding.performance.dto.PerformanceInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("performance")
@RequiredArgsConstructor
@Transactional
public class PerformanceController {
    private final PerformanceService performanceService;

    /**
     * Retrieves a list of all PerformanceInfo objects.
     *
     * @param request The pagination information.
     * @return ResponseEntity containing a ResponseHandler object with the list of PerformanceInfo objects.
     */
    @GetMapping
    public ResponseEntity<ResponseHandler<Page<PerformanceSearchResponse>>> getAllPerformanceResponseList(PerformanceSearchRequest request, PageRequest pageable) {
        log.debug("getAllPerformanceInfoList");
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<Page<PerformanceSearchResponse>>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(performanceService.getAllPerformanceInfoList(request.toDto(), pageable).map(PerformanceSearchResponse::of))
                        .build()
                );
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponseHandler<PerformanceResponse>> getPerformanceById(@PathVariable(value = "uuid") String uuid) {
        PerformanceInfo performanceInfo = performanceService.getPerformanceById(UUID.fromString(uuid));
        PerformanceResponse response = PerformanceResponse.of(performanceInfo);
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<PerformanceResponse>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(response)
                        .build()
                );
    }

    /**
     * Creates a new performance using the provided performance request.
     *
     * @param request The performance request containing the performance details.
     * @return ResponseEntity containing the response handler object with the performance response.
     */
    @PostMapping
    public ResponseEntity<ResponseHandler<PerformanceResponse>> createPerformance(@RequestBody PerformanceRequest request) {
        PerformanceResponse response = PerformanceResponse.of(performanceService.createPerformance(request.toDto()));
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<PerformanceResponse>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(response)
                        .build()
                );
    }

    /**
     * Updates the performance with the given performance ID using the provided performance info.
     *
     * @param uuid    The UUID of the performance to update.
     * @param request The updated performance request.
     * @return The updated performance response wrapped in a ResponseEntity and ResponseHandler object.
     */
    @PutMapping("/{uuid}")
    public ResponseEntity<ResponseHandler<PerformanceResponse>> updatePerformance(@PathVariable(value = "uuid") String uuid, PerformanceRequest request) {
        PerformanceInfo performanceInfo = request.toDto();
        PerformanceInfo updatedPerformance = performanceService.updatePerformance(UUID.fromString(uuid), performanceInfo);
        PerformanceResponse response = PerformanceResponse.of(updatedPerformance);
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<PerformanceResponse>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(response)
                        .build()
                );
    }

    /**
     * Deletes the performance with the given UUID.
     *
     * @param uuid The UUID of the performance to be deleted.
     * @return A ResponseEntity containing a ResponseHandler object with a success message and status code 200 if the performance was deleted successfully.
     * If the performance does not exist or if an error occurs during deletion, a different status code will be returned.
     */
    @DeleteMapping("/{uuid}")
    public ResponseEntity<ResponseHandler<String>> deletePerformance(@PathVariable(value = "uuid") String uuid) {
        performanceService.deletePerformance(UUID.fromString(uuid));
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<String>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data("Performance deleted successfully")
                        .build()
                );
    }
}
