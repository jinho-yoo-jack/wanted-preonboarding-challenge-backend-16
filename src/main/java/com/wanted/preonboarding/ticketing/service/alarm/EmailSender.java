package com.wanted.preonboarding.ticketing.service.alarm;

import com.wanted.preonboarding.ticketing.aop.advice.exception.DefaultException;
import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import com.wanted.preonboarding.ticketing.domain.dto.AlarmInfo;
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
public class EmailSender implements AlarmSender {
    private static final String EMAIL_SUBJECT = " 예약 알림";
    private static final String EMAIL_TEMPLATE_NAME = "email";
    private static final String PERFORMANCE_VARIABLE_NAME = "performance";
    private static final String PERFORMANCE_SEAT_INFO_VARIABLE_NAME = "performanceSeatInfo";

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendAlarm(AlarmInfo alarmInfo, PerformanceSeatInfo performanceSeatInfo) {
        EmailPerformance emailPerformance = performanceSeatInfo.getPerformance().from();
        EmailPerformanceSeatInfo emailPerformanceSeatInfo = performanceSeatInfo.from();

        sendEmail(alarmInfo, emailPerformance, emailPerformanceSeatInfo);
    }

    private void sendEmail(AlarmInfo alarmInfo, EmailPerformance emailPerformance, EmailPerformanceSeatInfo emailPerformanceSeatInfo) {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            helper.setTo(alarmInfo.getEmail());
            helper.setSubject(emailPerformance.getName() + EMAIL_SUBJECT);
            helper.setText(writeBody(emailPerformance, emailPerformanceSeatInfo), true);

            javaMailSender.send(mailMessage);
        } catch (MessagingException e) {
            throw new DefaultException(ErrorCode.FAILED_SEND_EMAIL);
        }
    }

    private String writeBody(EmailPerformance emailPerformance, EmailPerformanceSeatInfo emailPerformanceSeatInfo) {
        Context context = new Context();

        context.setVariable(PERFORMANCE_VARIABLE_NAME, emailPerformance);
        context.setVariable(PERFORMANCE_SEAT_INFO_VARIABLE_NAME, emailPerformanceSeatInfo);

        return templateEngine.process(EMAIL_TEMPLATE_NAME, context);
    }
}
