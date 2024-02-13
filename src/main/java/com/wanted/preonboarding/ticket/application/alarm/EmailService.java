package com.wanted.preonboarding.ticket.application.alarm;

import com.wanted.preonboarding.ticket.domain.dto.request.MailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;
    private static final String EMAIL_SENDER = "tim2875@naver.com";
    private static final String SUBJECT_PREFIX = "알림: ";
    private static final String SUBJECT_POSTFIX = "의 잔여 좌석이 생겼습니다.";

    @Async
    public void sendMail(MailRequest request) {
        log.info("EmailService.request");
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(EMAIL_SENDER);
            helper.setSubject(SUBJECT_PREFIX + request.getPerformanceName() + SUBJECT_POSTFIX);
            helper.setTo(request.getEmail());
            helper.setText("""
                    <html>
                    <h2> 알림: 공연취소된 예약이 있습니다.</h2>
                    <ul>
                        <li>공연ID: %s</li>
                        <li>공연명: %s</li>
                        <li>회차: %s</li>
                        <li>예매 가능 좌석: %s%s</li>
                        <li>시작 일시: %s</li>
                    </ul>
                    </html>
                    """.formatted(request.getPerformanceId(),
                    request.getPerformanceName(),
                    request.getRound(),
                    request.getLine(),
                    request.getSeat(),
                    request.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))), true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new MailSendException("이메일 전송에 실패했습니다."); //TODO: 상수로 변경
        }
    }
}
