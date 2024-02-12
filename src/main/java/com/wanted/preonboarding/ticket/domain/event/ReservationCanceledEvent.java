package com.wanted.preonboarding.ticket.domain.event;

import com.wanted.preonboarding.ticket.application.NotificationService;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.application.dto.ReservationCancelParam;
import com.wanted.preonboarding.ticket.domain.entity.SeatInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * 예약 취소됨 이벤트
 * @see ReservationService#cancel(ReservationCancelParam)
 * @see NotificationService#notify(ReservationCanceledEvent)
 */
@Getter
@Builder
public class ReservationCanceledEvent {
    private UUID performanceId;
    private String performanceName;
    private SeatInfo seatInfo;
}
