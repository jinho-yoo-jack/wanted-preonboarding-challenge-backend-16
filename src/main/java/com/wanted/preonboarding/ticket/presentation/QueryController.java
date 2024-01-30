package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공연과 관련된 정보를 처리하는 REST 컨트롤러입니다.
 * 이 클래스는 {@link PerformanceInfo}를 응답하며, 공연에 대한 조회 기능을 제공합니다.
 * <p>이 컨트롤러는 '/query' 경로로 매핑되어 있으며, 다양한 조회 요청을 처리합니다.</p>
 */
@RestController
@RequestMapping("query")
@RequiredArgsConstructor
public class QueryController {
  private final TicketSeller ticketSeller;

  /**
   * 모든 공연 정보를 조회하여 반환하는 메서드입니다.
   * 이 메서드는 '/all/performance' 경로로 GET 요청을 받아 처리합니다.
   *
   * @return ResponseEntity 객체로 감싸진, 모든 공연 정보를 담고 있는 {@link ResponseHandler}입니다.
   *         이 응답에는 HTTP 상태 코드와 공연 정보 리스트가 포함됩니다.
   */
  @GetMapping("/all/performance")
  public ResponseEntity<ResponseHandler<List<PerformanceInfo>>> getAllPerformanceInfoList() {
    System.out.println("getAllPerformanceInfoList");
    return ResponseEntity
        .ok()
        .body(ResponseHandler.<List<PerformanceInfo>>builder()
            .message("Success")
            .statusCode(HttpStatus.OK)
            .data(ticketSeller.getAllPerformanceInfoList())
            .build()
        );
  }
}
