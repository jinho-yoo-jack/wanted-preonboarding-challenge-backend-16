package com.wanted.preonboarding.layered.controller;

import com.wanted.preonboarding.domain.exception.TicketException;
import com.wanted.preonboarding.domain.response.ErrorResponseHandler;
import com.wanted.preonboarding.domain.response.ResponseHandler;
import com.wanted.preonboarding.domain.dto.LinkInfo;
import com.wanted.preonboarding.domain.dto.UserDto;
import com.wanted.preonboarding.domain.dto.performance.PerformanceSeatDto;
import com.wanted.preonboarding.domain.dto.reservation.NotificationDto;
import com.wanted.preonboarding.domain.dto.reservation.CreateReservationDto;
import com.wanted.preonboarding.domain.dto.reservation.NotificationResponseDto;
import com.wanted.preonboarding.domain.dto.reservation.ReserveResponseDto;
import com.wanted.preonboarding.domain.dto.reservation.ReservedListDto;
import com.wanted.preonboarding.layered.service.notification.NotificationService;
import com.wanted.preonboarding.layered.service.reservation.ReservationService;
import com.wanted.preonboarding.layered.service.discount.DefaultDiscountPolicy;
import com.wanted.preonboarding.layered.service.discount.DiscountPolicy;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.wanted.preonboarding.domain.dto.DtoSchemaSerializer.getClassSchema;


/**
 * 공연 예약과 관련된 정보를 처리하는 REST 컨트롤러로, /reserve 경로로 매핑되어있습니다.
 */
@RestController
@RequestMapping("v1/reserve")
@RequiredArgsConstructor
@Slf4j
public class ReserveController {
  final private ReservationService service;
  final private NotificationService notiService;

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
    UserDto user = UserDto.builder()
        .userName(name)
        .phoneNumber(phone)
        .build();
    return ResponseEntity.ok().body(
        ResponseHandler.<ReservedListDto>builder()
            .statusCode(HttpStatus.OK)
            .message("success")
            .data(this.service.reservedList(user))
            .build()
    );
  }

  /**
   * 예약을 실행한 후, 예약의 결과를 응답합니다.
   * POST 메서드로 요청을 받고, 예약의 결과를 반환합니다.
   *
   * @param createReservationDto 예약에 필요한 정보
   * @return 주어진 정보로 예약을 실행한 후, {@link PerformanceSeatDto}를 반환합니다.
   */
  @PostMapping
  public ResponseEntity<?> makeReservation(
      @RequestBody CreateReservationDto createReservationDto
  ) {
    DiscountPolicy policy = new DefaultDiscountPolicy();
    try {
      return ResponseEntity.ok().body(
          ResponseHandler.<ReserveResponseDto>builder()
              .statusCode(HttpStatus.OK)
              .message("예약 성공")
              //  TODO: 할인 정책 적용 방법 수정
              .data(this.service.createReservation(createReservationDto, policy))
              .build()
      );
    } catch (TicketException e) {
      throw e;
    } catch (RuntimeException e) {
      Map<String, LinkInfo> links = new HashMap<>();
      links.put("예약하기", LinkInfo.builder()
          .schema(getClassSchema(CreateReservationDto.class))
          .build());
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(ErrorResponseHandler.builder()
              .statusCode(HttpStatus.BAD_REQUEST)
              .message("올바른 요청 바람")
              .links(links)
              .build()
          );
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteReservation(
      @PathVariable("id") int id
  ) {
    log.info("id : {}", id);
    this.service.deleteReservation(id);
    return ResponseEntity.ok().body("삭제 완료");
  }

  @PostMapping("/notice")
  public ResponseEntity<ResponseHandler<NotificationResponseDto>> makeNotification(
      @RequestBody NotificationDto dto
  ) {
    NotificationResponseDto responseDto = this.notiService.register(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ResponseHandler.<NotificationResponseDto>builder()
            .statusCode(HttpStatus.CREATED)
            .message("알림 설정 완료")
            .data(responseDto)
            .build());
  }

  @DeleteMapping("/notice/{notificationId}")
  public ResponseEntity<?> cancelNotification(
      @PathVariable("notificationId") long id
  ) {
    this.notiService.unregister(id);
    return ResponseEntity.ok().body(
        ResponseHandler.builder()
            .statusCode(HttpStatus.OK)
            .message("예약 알림 제거")
            .build()
    );
  }
}
