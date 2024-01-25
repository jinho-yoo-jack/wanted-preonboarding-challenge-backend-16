package com.wanted.preonboarding.ticket.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.base.rsData.RsData;
import com.wanted.preonboarding.ticket.domain.dto.RequestNotification;
import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.NotificationRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
	private final NotificationRepository notificationRepository;

	private final PerformanceRepository performanceRepository;

	private final EmailService emailService;

	/*
		알림 설정 정보를 저장하는 data 생성 메서드
	 */
	@Transactional
	public RsData create(RequestNotification requestNotification) {
		Performance performance = performanceRepository.findById(requestNotification.getPerformanceId()).get();

		if (performance == null) {
			return RsData.of("F-1", "존재하지 않는 공연입니다.");
		}

		Notification notification = Notification.builder()
			.email(requestNotification.getEmail())
			.phoneNumber(requestNotification.getPhoneNumber())
			.name(requestNotification.getName())
			.performance(performance)
			.build();

		notificationRepository.save(notification);

		return RsData.of("S-1", String.format("%s님의 알림 설정 성공", requestNotification.getName()), notification);
	}

	/*
		취소 표가 생겼을 때 알림 이메일 보내는 메서드
	 */
	public void whenCancleTicketEvent(Performance performance, PerformanceSeatInfo performanceSeatInfo) throws
		MessagingException {
		List<Notification> performanceNotificationList = notificationRepository.findByPerformance(performance);
		if (performanceNotificationList.isEmpty()) {
			return;
		}
		// 수신자 설정
		List<String> receivers = new ArrayList<>();
		for (Notification notification : performanceNotificationList) {
			receivers.add(notification.getEmail());
		}

		String message = genMsg(performance, performanceSeatInfo);
		emailService.sendEmail(receivers, performance.getName() + " - " + performance.getRound() + "회차 공석 발생 알림",
			message);
	}

	/*
		공연 예매 정보를 포함하는 메일 메세지를 반환하는 메서드
	 */
	private String genMsg(Performance performance, PerformanceSeatInfo performanceSeatInfo) {
		String name = performance.getName();
		int round = performance.getRound();
		int gate = performanceSeatInfo.getGate();
		String line = performanceSeatInfo.getLine();
		int seat = performanceSeatInfo.getSeat();
		LocalDateTime time = performance.getStart_date();

		return "<html>\n"
			+ "<head>\n"
			+ "<title>공연 예매 알림</title>\n"
			+ "<style>\n"
			+ "body {\n"
			+ "font-family: Arial, sans-serif;\n"
			+ "}\n"
			+ ".container {\n"
			+ "width: 80%;\n"
			+ "margin: 0 auto;\n"
			+ "}\n"
			+ ".message {\n"
			+ "background-color: #f8f9fa;\n"
			+ "padding: 20px;\n"
			+ "margin: 20px 0;\n"
			+ "border-radius: 5px;\n"
			+ "border: 1px solid #dee2e6;\n"
			+ "}\n"
			+ ".footer {\n"
			+ "font-size: 0.8em;\n"
			+ "color: #6c757d;\n"
			+ "}\n"
			+ "</style>\n"
			+ "</head>\n"
			+ "<body>\n"
			+ "<div class=\"container\">\n"
			+ "<div class=\"message\">\n"
			+ "<h1>공연 예매 알림</h1>\n"
			+ "<p>\"" + name + " - " + round + "회차 취소 티켓이 발생하여 아래의 티켓 정보를 알려드립니다.\"</p>\n"
			+ "<ul>\n"
			+ "<li>시간: " + time + "</li>\n"
			+ "<li>좌석: " + gate + "번 게이트 " + line + "번째 줄 " + seat + "열" + "</li>\n"
			+ "</ul>\n"
			+ "<p>\"선착순으로 마감되기 때문에 예매를 서둘러주세요!\"</p>\n"
			+ "</div>\n"
			+ "<div class=\"footer\">\n"
			+ "<p>\"이 이메일은 해당 공연의 알림을 설정해둔 사용자에게만 발송되었습니다\"</p>\n"
			+ "</div>\n"
			+ "</div>\n"
			+ "</body>\n"
			+ "</html>";
	}
}
