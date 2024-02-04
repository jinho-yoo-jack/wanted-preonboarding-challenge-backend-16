package com.wanted.preonboarding.ticket.dto.request.notification;

import com.wanted.preonboarding.ticket.application.notification.model.NotificationMessageFormat;
import com.wanted.preonboarding.ticket.dto.result.CancelReservationInfo;

public record ReservationCancelEvent(CancelReservationInfo reservationInfo) implements NotificationHolder {

    @Override
    public String getMessage() {
        return NotificationMessageFormat.getEmptyPerformanceMessage(reservationInfo);
    }

    @Override
    public String getTargetId() {
        return reservationInfo.performanceId().toString();
    }
}
