package com.wanted.preonboarding.ticket.application.notification.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.wanted.preonboarding.ticket.dto.result.CancelReservationInfo;
import java.time.LocalDateTime;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NotificationMessageFormatTest {

    @DisplayName("지정된 형식으로 알림 메시지를 만들 수 있다.")
    @Test
    void getEmptyPerformanceMessage_success() {
        // given
        CancelReservationInfo info = CancelReservationInfo.builder()
            .performanceId(UUID.fromString("b50862c2-7dd9-4889-b88a-6c7585a007ac"))
            .name("레베카")
            .startDate(LocalDateTime.of(2023, 11, 10, 11, 0))
            .price(100_000)
            .round(1)
            .line("A")
            .seat(1)
            .build();

        // when
        String message = NotificationMessageFormat.getEmptyPerformanceMessage(info);
        System.out.println(message);

        // then
        assertThat(message).isEqualTo("""
            알림 등록하신 공연 & 전시의 빈 좌석이 생겼습니다.
            ID : b50862c2-7dd9-4889-b88a-6c7585a007ac
            공연 & 전시명 : 레베카
            일시 : 2023-11-10T11:00
            회차 : 1
            좌석 정보 : A행 1열
            가격 : 100000
                        
            본 알림은 단순히 빈 좌석이 발생했다는 알림이며,
            예매는 선착순으로 진행됩니다.
            """);
    }
}