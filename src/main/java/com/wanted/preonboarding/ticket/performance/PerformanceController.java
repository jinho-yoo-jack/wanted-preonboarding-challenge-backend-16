package com.wanted.preonboarding.ticket.performance;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * '/performance'로 공연의 정보를 반환하는 컨트롤러 객체입니다.
 * '/' 에 대해 Performance의 List를 반환하며,
 * '/{id}에 대해 해당 id를 가지는 공연의 상세 정보를 반환합니다.
 */
@RestController
@RequestMapping("v1/performance")
public class PerformanceController {
  private final PerformanceRepository repository;

  @Autowired
  public PerformanceController(PerformanceRepository repository) {
    this.repository = repository;
  }

  /**
   * '/'을 통해 요청을 받아 모든 공연의 리스트를 반환합니다.
   *
   * @return 모든 {@Performance}의 리스트
   */
  @GetMapping("/")
  public ResponseEntity<ResponseHandler<List<Performance>>> getPerformanceList() {
    return ResponseEntity.ok()
        .body(ResponseHandler.<List<Performance>>builder()
        .message("success")
        .statusCode(HttpStatus.OK)
        .data(this.repository.findAll())
        .build());
  }

  /**
   * {@link Performance}의 id를 받아 해당 공연의 상세 정보를 반환합니다.
   *
   * @param id 공연의 id.
   * @return 공연의 상세 정보
   */
  @GetMapping("/{id}")
  public ResponseEntity<ResponseHandler<Performance>> getPerformanceDetail(@PathVariable("id") UUID id) {
    return ResponseEntity.ok()
        .body(ResponseHandler.<Performance>builder()
            .statusCode(HttpStatus.OK)
            .message("success")
            .data(this.repository.findById(id).orElse(null))
            .build()
        );
  }
}
