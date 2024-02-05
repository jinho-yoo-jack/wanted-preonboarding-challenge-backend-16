package com.wanted.preonboarding.ticket.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromMailAddress;

    @Async
    public void sendEmail(String to, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setFrom(fromMailAddress);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(message);
            log.info("Email sent successfully to: " + to);

        } catch (MessagingException e) {
            log.error("Failed to send email to: " + to + ", Error: " + e.getMessage());
        }
    }
}