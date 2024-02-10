package com.wanted.preonboarding.ticket.notification.mailing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceV1 implements MailService {
  final JavaMailSender mailSender;

  @Override
  public void sendMail(String mail, SimpleMailMessage message) {
    message.setTo(mail);
    this.mailSender.send(message);
  }
}
