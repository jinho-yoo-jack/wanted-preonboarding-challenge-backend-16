package com.wanted.preonboarding.ticket.domain.dto.request;

import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SendNotification {

    String performanceName;
    Integer round;
    Character line;
    Integer seat;
    List<String> emailList;

    public static SendNotification of(List<Notification> notificationList, Reservation reservation) {
        Notification notification = notificationList.get(0);
        return SendNotification.builder()
                .performanceName(notification.getPerformance().getName())
                .round(notification.getPerformance().getRound())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .emailList(notificationList.stream().map(Notification::getEmail).toList())
                .build();
    }
}
