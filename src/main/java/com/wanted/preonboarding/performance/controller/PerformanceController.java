package com.wanted.preonboarding.performance.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.performance.dto.request.PerformanceRequest;
import com.wanted.preonboarding.performance.dto.request.PerformanceSearchRequest;
import com.wanted.preonboarding.performance.dto.response.PerformanceResponse;
import com.wanted.preonboarding.performance.dto.response.PerformanceSearchResponse;
import com.wanted.preonboarding.performance.service.PerformanceService;
import com.wanted.preonboarding.performance.dto.PerformanceInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;

    /**
     * Retrieves a list of all PerformanceInfo objects.
     *
     * @param request The pagination information.
     * @return ResponseEntity containing a ResponseHandler object with the list of PerformanceInfo objects.
     */
    @GetMapping
    public ResponseEntity<ResponseHandler<Page<PerformanceSearchResponse>>> getAllPerformanceResponseList(
            PerformanceSearchRequest request, @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        log.debug("getAllPerformanceInfoList");
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<Page<PerformanceSearchResponse>>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(performanceService.getAllPerformanceInfoList(request.toDto(), Pageable.ofSize(10)).map(PerformanceSearchResponse::of))
                        .build()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseHandler<PerformanceResponse>> getPerformanceById(@PathVariable(value = "id") Long id) {
        PerformanceInfo performanceInfo = performanceService.getPerformanceById(id);
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
     * @param id    The ID of the performance to update.
     * @param request The updated performance request.
     * @return The updated performance response wrapped in a ResponseEntity and ResponseHandler object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseHandler<PerformanceResponse>> updatePerformance(@PathVariable(value = "id") Long id,@RequestBody PerformanceRequest request) {
        PerformanceInfo performanceInfo = request.toDto();
        PerformanceInfo updatedPerformance = performanceService.updatePerformance(id, performanceInfo);
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
     * Deletes a performance with the given ID.
     *
     * @param id The ID of the performance to be deleted.
     * @return A ResponseEntity object containing a ResponseHandler object with a success message and status code.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseHandler<String>> deletePerformance(@PathVariable(value = "id") Long id) {
        performanceService.deletePerformance(id);
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
