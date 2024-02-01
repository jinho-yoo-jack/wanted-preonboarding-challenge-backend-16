package com.wanted.preonboarding.ticket.application.common.template;

import com.wanted.preonboarding.ticket.domain.dto.request.SendNotification;

import static j2html.TagCreator.*;

public class MailTemplate {

    public static final String RESERVATION_CANCELLED_TITLE = "[Wanted Ticket] 취소 좌석 발생 안내";

    private MailTemplate() {
        throw new IllegalStateException("유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

    public static String createReservationCancelledContent(SendNotification notification) {
        return html(
                head(
                        meta().withCharset("UTF-8"),
                        title("취소 좌석 발생 안내"),
                        style("""
                                table { border-collapse: collapse; text-align: center;}
                                th, td { border: 1px solid black; padding: 5px; }
                                th { background-color: #f2f2f2; }
                                """)
                ),
                body(
                        div(
                                p("안녕하세요. 원티드 티켓입니다."),
                                p("알림을 설정해두신 공연/전시의 취소 좌석이 발생하였습니다."),
                                p("취소 좌석이 발생한 공연/전시 정보는 다음과 같습니다."),
                                table(
                                        tr(
                                                th("공연명"),
                                                th("회차"),
                                                th("취소 좌석 정보")
                                        ),
                                        tr(
                                                td(notification.performanceName() + " (" + notification.performanceId() + ")"),
                                                td(notification.round() + "회차" + " (" + notification.startDate() + ")"),
                                                td(notification.line() + "열 " + notification.seat())
                                        )
                                ),
                                p("취소 좌석에 대한 예매는 선착순이오니, 예매를 원하시는 경우 원티드 티켓 홈페이지에서 예매를 진행해주세요."),
                                hr(),
                                i("이 알림은 알림을 신청하신 분들에게 일괄적으로 발송되는 메일입니다."),
                                br(),
                                i("이 메일은 발신 전용 메일입니다. 문의사항이 있으시다면, 원티드 티켓 홈페이지의 고객센터를 이용해주세요.")
                        ).withStyle("font-family: 'Noto Sans KR', sans-serif; width: 100%; height: 100%;")
                )).render();
    }

}
