package com.wanted.preonboarding.ticket.notification.mailing;

import org.springframework.mail.SimpleMailMessage;

public interface MailService {
  void  sendMail(String mail, SimpleMailMessage message);
}
