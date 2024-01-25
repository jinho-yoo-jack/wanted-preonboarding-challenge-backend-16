package com.wanted.preonboarding.ticket.application;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender mailSender;
	private final String ADMIN_ADDRESS = "r4560798@naver.com";

	@Async("taskExecutor1")
	public void sendEmail(List<String> recipients, String subject, String message) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		String[] to = recipients.toArray(new String[0]);
		// 테스트용 이메일일 경우 발송하지 않음
		if (to[0].contains("@test.com"))
			return;
		helper.setTo(to);  // 여러 수신자 설정
		helper.setFrom(ADMIN_ADDRESS);
		helper.setSubject(subject);
		helper.setText(message, true);  // true는 HTML 메일을 의미

		mailSender.send(mimeMessage);
	}
}
