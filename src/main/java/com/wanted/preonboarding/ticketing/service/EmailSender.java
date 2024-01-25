package com.wanted.preonboarding.ticketing.service;

import com.wanted.preonboarding.ticketing.domain.dto.email.EmailPerformance;
import com.wanted.preonboarding.ticketing.domain.dto.email.EmailPerformanceSeatInfo;
import com.wanted.preonboarding.ticketing.domain.entity.PerformanceSeatInfo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailSender {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendPerformanceInfo(String email, PerformanceSeatInfo performanceSeatInfo) {
        EmailPerformance emailPerformance = performanceSeatInfo.getPerformance().from();
        EmailPerformanceSeatInfo emailPerformanceSeatInfo = performanceSeatInfo.from();

        sendEmail(email, emailPerformance, emailPerformanceSeatInfo);
    }

    private void sendEmail(String email, EmailPerformance emailPerformance, EmailPerformanceSeatInfo emailPerformanceSeatInfo) {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            helper.setTo(email);
            helper.setSubject(emailPerformance.getName() + " 예약 알림");
            helper.setText(writeBody(emailPerformance, emailPerformanceSeatInfo), true);

            javaMailSender.send(mailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String writeBody(EmailPerformance emailPerformance, EmailPerformanceSeatInfo emailPerformanceSeatInfo) {
        Context context = new Context();

        context.setVariable("performance", emailPerformance);
        context.setVariable("performanceSeatInfo", emailPerformanceSeatInfo);

        return templateEngine.process("email", context);
    }
}
