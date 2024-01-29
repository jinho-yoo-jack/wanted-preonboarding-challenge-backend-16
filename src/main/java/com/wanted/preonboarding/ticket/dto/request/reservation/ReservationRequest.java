package com.wanted.preonboarding.ticket.dto.request.reservation;

import lombok.Builder;

/* Performance 예약 요청
* 고객의 이름, 휴대 전화, 결제 가능한 금액(잔고), 예약을 원하는 공연 또는 전시회ID, 회차, 좌석 정보
 */
@Builder
public record ReservationRequest(
    String name,
    String phone,
    int age,
    int costAmount,
    String performanceId,
    int round,
    String line,
    int seat
) {

}
