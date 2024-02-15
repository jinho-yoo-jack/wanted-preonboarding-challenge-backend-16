package com.wanted.preonboarding.layered.service.mailing;

import org.springframework.mail.SimpleMailMessage;

public interface MailService {
  void  sendMail(String mail, SimpleMailMessage message);
}
