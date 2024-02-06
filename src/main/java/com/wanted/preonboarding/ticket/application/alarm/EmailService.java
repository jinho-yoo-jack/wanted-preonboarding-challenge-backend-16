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

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;
    private static final String EMAIL_SENDER = "test@check.com";
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
            helper.setText("메일 본문", true);  //html: true

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new MailSendException("이메일 전송에 실패했습니다."); //TODO: 상수로 변경
        }
    }
}


