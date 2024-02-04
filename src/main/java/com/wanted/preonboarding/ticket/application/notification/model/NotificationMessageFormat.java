package com.wanted.preonboarding.ticket.application.notification.model;

import com.wanted.preonboarding.ticket.dto.result.CancelReservationInfo;

public enum NotificationMessageFormat {
    EMPTY_PERFORMANCE("""
            알림 등록하신 공연 & 전시의 빈 좌석이 생겼습니다.
            ID : %s
            공연 & 전시명 : %s
            일시 : %s
            회차 : %d
            좌석 정보 : %s행 %d열
            가격 : %d
            
            본 알림은 단순히 빈 좌석이 발생했다는 알림이며, 
            예매는 선착순으로 진행됩니다.
            """);

    private final String message;

    NotificationMessageFormat(String message) {
        this.message = message;
    }

    public static String getEmptyPerformanceMessage(CancelReservationInfo info) {
        return String.format(EMPTY_PERFORMANCE.message,
            info.performanceId().toString(),
            info.name(),
            info.startDate(),
            info.round(),
            info.line(), info.seat(),
            info.price());
    }
}
