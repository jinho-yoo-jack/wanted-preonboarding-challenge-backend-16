package com.wanted.preonboarding.ticket.domain.dto.request;

import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Builder;
import lombok.Value;

import java.util.List;

import static com.wanted.preonboarding.ticket.application.common.util.TimeFormatter.convertToReadableFormat;

@Value
@Builder
public class SendNotification {

    String performanceId;
    String performanceName;
    Integer round;
    String startDate;
    Character line;
    Integer seat;
    List<String> emailList;

    public static SendNotification of(List<Notification> notificationList, Reservation reservation) {
        Notification notification = notificationList.get(0);
        Performance performance = notification.getPerformance();
        return SendNotification.builder()
                .performanceId(performance.getId().toString())
                .performanceName(performance.getName())
                .round(performance.getRound())
                .startDate(convertToReadableFormat(performance.getStartDate()))
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .emailList(notificationList.stream().map(Notification::getEmail).toList())
                .build();
    }
}
