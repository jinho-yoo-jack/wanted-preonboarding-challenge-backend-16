package com.wanted.preonboarding.ticket.reservation;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.reservation.CreateReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공연 예약과 관련된 정보를 처리하는 REST 컨트롤러로, /reserve 경로로 매핑되어있습니다.
 */
@RestController
@RequestMapping("v1/reserve")
@RequiredArgsConstructor
public class ReserveController {
  /**
   * 고객의 정보를 받아 해당 고객의 예약들을 반환합니다.
   *
   * @param name 고객명
   * @param phone 휴대폰 번호
   * @return 고객의 예약 목록
   */
  @GetMapping
  public ResponseEntity<?> getReservationInfo(
      @RequestParam(value = "name") String name,
      @RequestParam(value = "phone") String phone
  ) {
    //  TODO : Valid phone Number
    System.out.println(name);
    System.out.println(phone);
    return ResponseEntity.ok().body(null);
  }

  /**
   * 예약을 실행한 후, 예약의 결과를 응답합니다.
   * POST 메서드로 요청을 받고, 예약의 결과를 반환합니다.
   *
   * @param createReservationDto 예약에 필요한 정보
   * @return 주어진 정보로 예약을 실행한 후, {@link TicketSeller}의 reserve 메소드 실행 결과를 반환합니다.
   */
  @PostMapping
  public ResponseEntity<?> makeReservation(
      @RequestBody CreateReservationDto createReservationDto
  ) {
    System.out.println(createReservationDto);
    return ResponseEntity.ok().body(createReservationDto);
  }
}
