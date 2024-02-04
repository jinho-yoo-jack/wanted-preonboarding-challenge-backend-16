package com.wanted.preonboarding.ticket.application.notification;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wanted.preonboarding.ticket.domain.notification.Notification;
import com.wanted.preonboarding.ticket.domain.notification.NotificationRepository;
import com.wanted.preonboarding.ticket.dto.request.notification.ReservationCancelEvent;
import com.wanted.preonboarding.ticket.dto.result.CancelReservationInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    NotificationService notificationService;

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    NotificationSender notificationSender;

    @DisplayName("알림을 전송할 수 있다.")
    @Test
    void sendNotification() {
        // given
        UUID performanceId = UUID.randomUUID();
        CancelReservationInfo info = CancelReservationInfo.builder()
            .performanceId(performanceId)
            .name("Reveca")
            .startDate(LocalDateTime.now())
            .round(1)
            .line("A")
            .seat(1)
            .price(100_000)
            .build();
        ReservationCancelEvent holder = new ReservationCancelEvent(info);

        given(notificationRepository.findAllByTargetId(any(String.class)))
            .willReturn(List.of(new Notification(performanceId.toString(), "기우", "01011112222")));


        // when
        notificationService.sendNotification(holder);

        // then
        verify(notificationRepository, times(1)).findAllByTargetId(any(String.class));
        verify(notificationSender).send(any(), any());
    }
}