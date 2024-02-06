package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.application.dto.MessageInfo;
import com.wanted.preonboarding.ticket.application.interfaces.AlarmSender;
import org.springframework.stereotype.Component;

@Component
public class StringAlarmSender implements AlarmSender {

    public static final String ALARM_MESSAGE_FORMAT =
            """
            [%s] 고객님, 고객님이 알림 등록한 [%s]의 공연의 여분 좌석이 생겼습니다.
            해당 공연의 정보는 다음과 같습니다.
            공연 명 : [%s]
            공연 시작 시간과 날짜 : [%s]
            공연 좌석 라인 : [%s]
            공연 좌석 번호 : [%d]
            공연 입장 게이트 : [%d]
            해당 공연의 예약은 선착순으로 마무리 되므로 예약이 보장되지는 않습니다.
            """;

    @Override
    public void sendMessage(MessageInfo messageInfo) {
        System.out.println(String.format(ALARM_MESSAGE_FORMAT,
                messageInfo.reservationName(),
                messageInfo.performanceName(),
                messageInfo.performanceName(),
                messageInfo.startDateTime(),
                messageInfo.line(),
                messageInfo.seat(),
                messageInfo.gate())
        );
    }
}
